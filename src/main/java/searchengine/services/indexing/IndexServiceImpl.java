package searchengine.services.indexing;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import searchengine.config.Site;
import searchengine.config.SitesList;
import searchengine.dto.indexing.SiteDto;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;
import searchengine.response.ErrorResponse;
import searchengine.response.Response;
import searchengine.services.indexing.site.SiteIndexer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static searchengine.model.Status.INDEXING;

@Service
@RequiredArgsConstructor
public class IndexServiceImpl implements IndexService {

    private final SitesList sitesList;
    private final SiteRepository siteRepository;
    private final PageRepository pageRepository;

    private SiteIndexer siteIndexer;
    private ExecutorService executorService;

    private static boolean isIndexingStart = false;

    @Override
    public Response startIndexing() throws Exception {

        if (!isIndexingStart) {
            executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            for (Site site : sitesList.getSites()) {
                siteIndexer = new SiteIndexer(site, siteRepository, pageRepository);
                siteRepository.deleteAll();
                pageRepository.deleteAll();
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


}
