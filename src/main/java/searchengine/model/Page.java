package searchengine.model;

import javax.persistence.*;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Index;
@Entity
@Table(name  = "pages")
@Setter
@Getter

public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "site_id",nullable = false)
    private int siteId;

    @Column(nullable = false,columnDefinition = "TEXT")
    private String path;
    @Column(nullable = false)
    private int code;
    @Column(nullable = false, columnDefinition = "MEDIUMTEXT")
    private String content;

    @OneToMany
    @JoinColumn(name = "page_id")
    private List<searchengine.model.Index> indexes;
}
