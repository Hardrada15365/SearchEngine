package searchengine.services.search;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import searchengine.config.SitesList;
import searchengine.indexingTools.lemma.Lemmatizator;
import searchengine.model.Page;
import searchengine.repository.IndexRepository;
import searchengine.repository.LemmaRepository;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;
import searchengine.response.Response;
import searchengine.response.SearchResponse;
import searchengine.searchTool.Search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private  final SiteRepository siteRepository;
    private  final PageRepository pageRepository;

    private  final LemmaRepository lemmaRepository;
    private  final IndexRepository indexRepository;

    private  final SitesList sitesList;


    @Override
    public Response search(String userQuery) throws IOException {


        Search search = new Search(siteRepository,pageRepository,lemmaRepository,indexRepository,sitesList,userQuery);


        System.out.println(search.filterLemmasByFrequency());
        System.out.println(search.sortByPercent());
        for (Page page : search.findPagesByLemma()){
            System.out.println(page.getPath());
        }

        search.relevanceCalculation();
        return new Response();
    }
}
