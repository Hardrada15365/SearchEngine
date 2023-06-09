package searchengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import searchengine.model.Site;

@Repository
public interface SiteRepository extends JpaRepository<Site, Integer> {

    @Transactional
    Site findByUrl(String url);

    @Transactional
    Site findById(int id);

    @Transactional
    @Modifying
    @Query(value = "delete from sites", nativeQuery = true)
    void delete();


    @Override
    @Transactional
    void deleteAll();



}
