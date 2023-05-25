package searchengine.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;


@Entity
@Table(name = "indexes_")
@Setter
@Getter
public class Index {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "page_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Page page_id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lemma_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Lemma lemma_id;
    @Column(name = "rank_", nullable = false, columnDefinition = "float")
    private float rank;

    public Index(){}

    public Index(Page page,float rank,Lemma lemma){
        this.page_id = page;
        this.rank = rank;
        this.lemma_id = lemma;
    }

}
