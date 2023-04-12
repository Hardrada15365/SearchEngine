package searchengine.model;

import javax.persistence.*;

@Entity
@Table(name = "lemmas")
public class Lemma {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private int site_id;
    @Column(nullable = false)
    private String lemma;
    @Column(nullable = false)
    private int frequency;

}
