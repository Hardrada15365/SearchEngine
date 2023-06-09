package searchengine.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lemmas")
@Setter
@Getter
public class Lemma {
    public Lemma(){}

    public Lemma (String lemma, Site siteId, int frequency){
        this.lemma  = lemma;
        this.siteId = siteId;
        this.frequency = frequency;

    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Site siteId;
    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String lemma;
    @Column(nullable = false)
    private int frequency;

    @OneToMany(mappedBy = "lemma_id", cascade = CascadeType.ALL)
    private List<Index> indexes = new ArrayList<>();

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (obj == null || getClass()!= obj.getClass()){
            return  false;
        }
        Lemma lemma1 = (Lemma) obj;
        return siteId == lemma1.siteId && lemma.equals(lemma1.lemma);
    }

}
