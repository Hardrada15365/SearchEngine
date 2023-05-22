package searchengine.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import searchengine.config.Site;
import searchengine.config.SitesList;
import searchengine.indexingTools.lemma.Lemmatizator;
import searchengine.model.Index;
import searchengine.model.Lemma;
import searchengine.model.Page;
import searchengine.repository.IndexRepository;
import searchengine.repository.LemmaRepository;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;
import searchengine.response.ErrorResponse;
import searchengine.response.Response;
import searchengine.indexingTools.site.SiteIndexer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class IndexServiceImpl implements IndexService {

    private final SitesList sitesList;
    private final SiteRepository siteRepository;
    private final PageRepository pageRepository;

    private final LemmaRepository lemmaRepository;
    private final IndexRepository indexRepository;

    private SiteIndexer siteIndexer;
    private ExecutorService executorService;

    private static boolean isIndexingStart = false;

    @Override
    public Response startIndexing() throws Exception {

        if (!isIndexingStart) {
            executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            for (Site site : sitesList.getSites()) {
                siteIndexer = new SiteIndexer(site, siteRepository, pageRepository, lemmaRepository, indexRepository);
                siteRepository.deleteAll();
                pageRepository.deleteAll();
                lemmaRepository.deleteAll();
                indexRepository.deleteAll();
                isIndexingStart = siteIndexer.isIndexingRun();
                executorService.submit(siteIndexer);
            }
            executorService.shutdown();
            return new Response();
        } else {
            return new ErrorResponse("Индексация запущенна!");
        }

    }

    @Override
    public Response stopIndexing() {
        if (isIndexingStart) {
            executorService.shutdownNow();
            siteIndexer.stopIndexing();
            isIndexingStart = false;
            return new Response();
        } else {
            return new ErrorResponse("Индексация не запущенна!");
        }
    }

    public Response indexPage(String urlPage) throws IOException {
        siteIndexer.indexPage(urlPage);
        return new Response();
}
}



