package searchengine.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "lemmas")
@Setter
@Getter
public class Lemma {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private int site_id;
    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String lemma;
    @Column(nullable = false)
    private int frequency;

    @OneToMany
    @JoinColumn(name = "lemma_id")
    private List<Index> lemmas;

}
