package searchengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import searchengine.model.Lemma;
import searchengine.model.Site;

import java.util.List;

@Repository
public interface LemmaRepository extends JpaRepository<Lemma,Integer> {

    @Transactional
    Lemma findByLemmaAndSiteId(String lemma, Site site);

    @Transactional
    List<Lemma> findByLemma(String lemma);

    @Transactional
    boolean existsByLemmaAndSiteId(String lemma, Site site);


    @Transactional
    @Modifying
    @Query(value = "delete from lemmas", nativeQuery = true)
    void delete();


    @Query(value = "select sum(frequency)\n" +
            "from lemmas\n" +
            "where lemma  = ?", nativeQuery = true)
    Integer frequencyOnAllSites(String lemma);

    @Override
    @Transactional
    void deleteAll();




}

