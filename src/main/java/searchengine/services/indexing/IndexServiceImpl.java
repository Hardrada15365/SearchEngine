package searchengine.services.indexing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import searchengine.config.Site;
import searchengine.config.SitesList;
import searchengine.response.Response;
import searchengine.repository.IndexIRepository;
import searchengine.repository.LemmaRepository;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;
import searchengine.services.indexing.site.SiteIndexer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class IndexServiceImpl implements IndexService{

    private final SitesList  sitesList;
    private  final SiteRepository siteRepository;
    private  final PageRepository pageRepository;

    private SiteIndexer siteIndexer;
    private  ExecutorService executorService;


    @Override
    public Response startIndexing() throws Exception {

        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (Site site:sitesList.getSites()){
            siteIndexer = new SiteIndexer(site,siteRepository,pageRepository);
            siteIndexer.clearRepositories();
            executorService.submit(siteIndexer);
        }
        executorService.shutdown();
        return new Response();
    }

    @Override
    public Response stopIndexing() {
        executorService.shutdownNow();
        siteIndexer.stopIndexing();

        return new Response();
    }

    @Override
    public Response indexPage() {
        return null;
    }

    @Override
    public Response statistics() {
        return null;
    }

    @Override
    public Response search() {
        return null;
    }
}
