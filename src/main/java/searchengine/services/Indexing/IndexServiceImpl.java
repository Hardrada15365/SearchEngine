package searchengine.services.Indexing;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import searchengine.config.Site;
import searchengine.config.SitesList;
import searchengine.repository.IndexRepository;
import searchengine.repository.LemmaRepository;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;
import searchengine.response.ErrorResponse;
import searchengine.response.Response;
import searchengine.indexingTools.site.SiteIndexer;

import java.io.IOException;
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
       clearAllRepositories();
        if (!isIndexingStart) {
            executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

            for (Site site : sitesList.getSites()) {
                siteIndexer = new SiteIndexer(site, siteRepository, pageRepository,lemmaRepository,indexRepository);
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
        boolean isUrlFromBaseSites = false;
        for (Site site : sitesList.getSites()) {
            if (urlPage.contains(site.getUrl())){
                isUrlFromBaseSites = true;
            }
        }

        if (!isUrlFromBaseSites){
        siteIndexer.indexPage(urlPage);
            return new Response();
        }else{
            return  new ErrorResponse("Данная страница находится за пределами сайтов, \n" +
                    "указанных в конфигурационном файле");
        }

    }

    private void clearAllRepositories(){
        indexRepository.delete();
        pageRepository.delete();
        lemmaRepository.delete();
        siteRepository.delete();
    }





}
