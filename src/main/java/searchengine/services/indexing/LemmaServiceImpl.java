package searchengine.services.indexing;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import searchengine.model.Index;
import searchengine.model.Lemma;
import searchengine.model.Page;
import searchengine.model.Site;
import searchengine.repository.LemmaRepository;
import searchengine.repository.PageRepository;
import searchengine.response.Response;
import searchengine.services.indexing.lemma.Lemmatizator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.util.Arrays.asList;

@Service
@RequiredArgsConstructor
public class LemmaServiceImpl implements LemmaService {
    private final LemmaRepository lemmaRepository;
    private final PageRepository pageRepository;
    private final LemmaRepository indexRepository;


    @Override
    public Response indexPage(String urlPage) throws IOException {
        Lemmatizator lemmatizator = new Lemmatizator(urlPage);

        if (pageRepository.existsByPath(urlPage)) {
            Page oldPage = pageRepository.findByPath(urlPage);
            Site site = oldPage.getSiteId();
            pageRepository.delete(pageRepository.findByPath(urlPage));

            Page page = new Page(site, urlPage, lemmatizator.getCode(), lemmatizator.getContent());
            pageRepository.save(page);

            HashMap<String, Integer> hashLemmas = lemmatizator.getLemmas();

            System.out.println(hashLemmas);

            for (String key : hashLemmas.keySet()) {
                Lemma lemma = new Lemma(key, site);
                if (lemmaRepository.existsByLemmaAndSiteId(key, site)) {
                    if (lemmaRepository.findByLemmaAndSiteId(key, site).equals(lemma)) {
                        Lemma updatedLemma = lemmaRepository.findByLemmaAndSiteId(key, site);
                        lemmaRepository.delete(updatedLemma);
                        int newFrequency = updatedLemma.getFrequency() + 1;
                        updatedLemma.setFrequency(newFrequency);
                        lemmaRepository.save(updatedLemma);
                    }
                } else {
                    lemma.setFrequency(1);
                    lemmaRepository.saveAndFlush(lemma);
                }

            }
            return new Response();
        } else {
            return new Response();
        }
    }
}
