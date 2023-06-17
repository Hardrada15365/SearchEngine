package searchengine.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import searchengine.model.Lemma;
import searchengine.model.Page;

import java.util.List;

public interface PageRepository extends JpaRepository<Page,Integer> {

    @Transactional
    Page findByPath(String url);

    @Transactional
    Page findById(int PageId);

    @Transactional
    boolean existsByPath(String path);

    @Transactional
    @Modifying
    @Query(value = "delete from pages", nativeQuery = true)
    void delete();


    @Query(value = "select count(*)\n" +
            "from pages\n" +
            "where site_id = 1? ", nativeQuery = true)
    int pageCountBySiteId(int site_id);


    @Query(value = "select count(*)" +
                   "from pages", nativeQuery = true)
    int pageCount();

    @Query(value =
            "select indexes_.page_id " +
            " from indexes_, " +
            "     pages," +
            "     lemmas" +
            " where indexes_.page_id = pages.id" +
            " and indexes_.lemma_id = lemmas.id" +
            " and lemma = ?",nativeQuery = true)
    List<Integer> pagesIdByLemma (String lemma);

    @Override
    @Transactional
    void deleteAll();
}
