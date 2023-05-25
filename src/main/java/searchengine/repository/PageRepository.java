package searchengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import searchengine.model.Page;

public interface PageRepository extends JpaRepository<Page,Integer> {

    @Transactional
    Page findByPath(String url);

    @Transactional
    boolean existsByPath(String path);


    @Transactional
    @Modifying
    @Query(value = "delete from pages", nativeQuery = true)
    void delete();
}
