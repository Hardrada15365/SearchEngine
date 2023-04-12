package searchengine.model;

import javax.persistence.*;

@Entity
@Table(name  = "pages")
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "site_id",nullable = false)
    private int siteId;
    @Column(nullable = false)
    private String path;
    @Column(nullable = false)
    private int code;
    @Column(nullable = false)
    private String content;
}
