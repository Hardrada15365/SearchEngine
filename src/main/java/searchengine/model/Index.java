package searchengine.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "indexes_")
@Setter
@Getter
public class Index {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private int page_id;
    @Column(nullable = false)
    private int lemma_id;
    @Column(name = "rank_", nullable = false, columnDefinition = "float")
    private float rank;



}
