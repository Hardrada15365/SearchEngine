package searchengine.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import searchengine.model.Index;
import searchengine.model.Lemma;
import searchengine.model.Page;

import java.util.List;

@Repository
public interface IndexRepository extends JpaRepository<Index,Integer> {

    @Transactional
    @Modifying
    @Query(value = "delete from indexes_", nativeQuery = true)
    void delete();


    @Query(value = "select sum(rank_)"+
                   " from indexes_" +
                   " where page_id  = ?",nativeQuery = true)
    Double sumRank(int pageId);



    @Override
    @Transactional
    void deleteAll();

}
