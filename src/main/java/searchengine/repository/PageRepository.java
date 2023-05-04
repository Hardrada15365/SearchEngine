package searchengine.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import searchengine.model.Page;

public interface PageRepository extends CrudRepository<Page,Integer> {

    @Transactional
    Page findByPath(String url);
}
