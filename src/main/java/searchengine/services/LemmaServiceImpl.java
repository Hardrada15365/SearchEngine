package searchengine.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import searchengine.model.Index;
import searchengine.model.Lemma;
import searchengine.model.Page;
import searchengine.model.Site;
import searchengine.repository.IndexRepository;
import searchengine.repository.LemmaRepository;
import searchengine.repository.PageRepository;
import searchengine.response.Response;
import searchengine.indexingTools.lemma.Lemmatizator;

import java.io.IOException;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class LemmaServiceImpl implements LemmaService {
    private final LemmaRepository lemmaRepository;
    private final PageRepository pageRepository;
    private final IndexRepository indexRepository;


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
                int rank = hashLemmas.get(key);
                Lemma lemma = new Lemma(key,site,1);
                Index index = new Index(page,rank,lemma);

                indexRepository.save(index);
                lemmaRepository.save(lemma);

            }
            return new Response();
        } else {
            return new Response();
        }
    }
}
