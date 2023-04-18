package searchengine.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import searchengine.config.Site;
import searchengine.config.SitesList;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.repository.SiteRepository;
import searchengine.services.PageJoiner;
import searchengine.services.SiteTasks;
import searchengine.services.StatisticsService;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

@RestController
@RequestMapping("/api")
public class ApiController  {

    @Autowired
    private SiteRepository siteRepository;
    @Autowired
    private SitesList sitesList;


    private final StatisticsService statisticsService;
    private volatile Set<String> AllPages = new HashSet<>();

    public ApiController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/statistics")
    public ResponseEntity<StatisticsResponse> statistics() {
        return ResponseEntity.ok(statisticsService.getStatistics());
    }

    @GetMapping("/startIndexing")
    public ResponseEntity startIndexing() throws InterruptedException {
        PageJoiner pageJoiner = null;
        for (Site site:sitesList.getSites()){
            pageJoiner = new PageJoiner(site.getUrl());
            pageJoiner.start();
        }
        pageJoiner.join();
        System.out.println(pageJoiner.getAllPages().size());

        return new ResponseEntity("Метод работает", HttpStatus.OK);
    }
}
