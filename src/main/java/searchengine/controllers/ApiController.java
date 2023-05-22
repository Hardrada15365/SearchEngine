package searchengine.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.response.Response;
import searchengine.services.IndexService;
import searchengine.services.IndexServiceImpl;
import searchengine.services.LemmaService;
import searchengine.services.statistic.StatisticsService;

import java.io.IOException;


@RestController
@RequestMapping("/api")
public class ApiController {


    private final StatisticsService statisticsService;

    private final IndexService indexService;
    private final LemmaService lemmaService;

    private boolean isIndexingStart;

    public ApiController(StatisticsService statisticsService, IndexServiceImpl indexService, LemmaService lemmaService) {
        this.statisticsService = statisticsService;
        this.indexService = indexService;
        this.lemmaService = lemmaService;
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

        indexService.indexPage(url);
        System.out.println("Индексация страницы");

        return new Response();
    }

    @GetMapping("search")
    public Response search() {
        return new Response();
    }
}
