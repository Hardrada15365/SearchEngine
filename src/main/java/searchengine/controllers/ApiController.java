package searchengine.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.response.Response;
import searchengine.services.Indexing.IndexService;
import searchengine.services.Indexing.IndexServiceImpl;
import searchengine.services.search.SearchService;
import searchengine.services.statistic.StatisticsService;

import java.io.IOException;


@RestController
@RequestMapping("/api")
public class ApiController {


    private final StatisticsService statisticsService;

    private final IndexService indexService;

    private final SearchService searchService;


    public ApiController(StatisticsService statisticsService, IndexServiceImpl indexService,SearchService searchService) {
        this.statisticsService = statisticsService;
        this.indexService = indexService;
        this.searchService = searchService;
    }

    @GetMapping("/statistics")
    public ResponseEntity<StatisticsResponse> statistics() {
        return ResponseEntity.ok(statisticsService.getStatistics());
    }

    @GetMapping("/startIndexing")
    public Response startIndexing() throws Exception {
        return indexService.startIndexing();
    }

    @GetMapping("/stopIndexing")
    public Response stopIndexing() {
        return indexService.stopIndexing();

    }

    @PostMapping("/indexPage")
    public Response indexPage(@RequestParam(name = "url") String url) throws IOException {

        System.out.println("Индексация страницы");

        return indexService.indexPage(url);
    }

    @GetMapping("/search")
    public Response search(@RequestParam(name = "query") String query) throws IOException {
        System.out.println("Поиск");
        return searchService.search(query);
    }

}
