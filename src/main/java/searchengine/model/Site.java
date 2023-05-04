package searchengine.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sites")
@Setter
@Getter
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false,columnDefinition = "VARCHAR(255)")
    private String name;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "status_time",columnDefinition = "DATETIME")
    private Date statusTime;
    @Column(name = "last_error",columnDefinition = "TEXT")
    private String lastError;
    @Column(nullable = false,columnDefinition = "VARCHAR(255)")
    private String url;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "siteId", cascade = CascadeType.ALL)
    private List<Page> pages = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "siteId", cascade = CascadeType.ALL)
    private  List<Lemma> lemmas = new ArrayList<>();




}
