package searchengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import searchengine.model.Site;

@Repository
public interface SiteRepository extends CrudRepository<Site, Integer> {

    @Transactional
    Site findByUrl(String url);

    @Transactional
    Site findById(int id);
}
