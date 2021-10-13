package com.brightics.prj.web.repository;

import com.brightics.prj.web.entity.Candidate;
import com.brightics.prj.web.entity.Stock;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock,Long> {
    List<Stock> findStockByCandidateIs(Candidate candidate);

    @Query("select s from Stock s left join fetch s.candidate where s.code=:code")
    Optional<Stock> findStockByCodeIs(@Param("code") String code);

    @EntityGraph(attributePaths = {"candidate"})
    List<Stock> findAll();


}
