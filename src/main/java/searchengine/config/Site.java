package searchengine.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import searchengine.services.SiteParser;
import searchengine.services.SiteTasks;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

@Setter
@Getter
public class Site {
    private String url;
    private String name;


}

