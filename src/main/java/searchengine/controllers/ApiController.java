package searchengine.controllers;

import com.sun.net.httpserver.SimpleFileServer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.response.Response;
import searchengine.services.indexing.IndexService;
import searchengine.services.indexing.IndexServiceImpl;
import searchengine.services.indexing.LemmaService;
import searchengine.services.statistic.StatisticsService;

import java.io.IOException;


@RestController
@RequestMapping("/api")
public class ApiController  {


    private final StatisticsService statisticsService;

    private final IndexService indexService;
    private final LemmaService lemmaService;

    public ApiController(StatisticsService statisticsService, IndexServiceImpl indexService,LemmaService lemmaService) {
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
        indexService.startIndexing();

        return new Response();
    }

    @GetMapping("/stopIndexing")
    public  ResponseEntity stopIndexing(){
        indexService.stopIndexing();
        System.out.println("Остановлено?");
        return new ResponseEntity("Метод работает",HttpStatus.OK);
    }

    @PostMapping("/indexPage")
    public ResponseEntity indexPage (@RequestParam(name = "url") String url) throws IOException {

        lemmaService.indexPage(url);
        System.out.println("Индексация страницы");
        return new ResponseEntity("Метод работает",HttpStatus.OK);
    }
}
