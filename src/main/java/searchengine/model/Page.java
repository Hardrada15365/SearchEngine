package searchengine.model;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name  = "pages")
@Setter
@Getter

public class Page  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "site_id", nullable = false, referencedColumnName = "id")
    private Site siteId;

    @Column(nullable = false,columnDefinition = "VARCHAR(256)")
    private String path;
    @Column(nullable = false)
    private int code;
    @Column(nullable = false, columnDefinition = "MEDIUMTEXT")
    private String content;

    @OneToMany(mappedBy = "page_id", cascade = CascadeType.ALL)
    private List<searchengine.model.Index> indexes = new LinkedList<>();

    public Page(Site siteId, String path, int code, String content) {
        this.siteId = siteId;
        this.path = path;
        this.code = code;
        this.content = content;
    }
    public Page(){
    }
}


