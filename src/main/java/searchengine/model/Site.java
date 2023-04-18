package searchengine.model;

import lombok.Getter;
import lombok.Setter;
import searchengine.services.SiteParser;

import javax.persistence.*;
import java.io.IOException;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "sites")
@Setter
@Getter
public class Site {


    public Site(String url){
        this.url = url;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false,columnDefinition = "VARCHAR(255)")
    private String name;
    @Enumerated()
    @Column(columnDefinition = "ENUM('INDEXING', 'INDEXED', 'FAILED')")
    private Status status;
    @Column(name = "status_time",columnDefinition = "DATETIME")
    private Date statusTime;
    @Column(name = "last_error",columnDefinition = "TEXT")
    private String lastError;
    @Column(nullable = false,columnDefinition = "VARCHAR(255)")
    private String url;

    @OneToMany
    @JoinColumn(name = "site_id")
    private List<Page> pages;

    @OneToMany
    @JoinColumn(name = "site_id")
    private  List<Lemma> lemmas;
    public Set<Site> getPages() throws IOException, InterruptedException {
        Set<Site> siteSet = new HashSet<>();

        for (String link: SiteParser.getLinks(url)){
            Site newSite = new Site(link);
            siteSet.add(newSite);
        }

        return siteSet;
    }



}
