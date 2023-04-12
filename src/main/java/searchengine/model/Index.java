package searchengine.model;

import javax.persistence.*;

@Entity
@Table(name = "indexes")
public class Index {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private int page_id;
    @Column(nullable = false)
    private int lemma_id;
    @Column(nullable = false)
    private float rank;

}
