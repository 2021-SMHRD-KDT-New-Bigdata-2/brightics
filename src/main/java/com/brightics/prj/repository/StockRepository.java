package com.brightics.prj.repository;

import com.brightics.prj.entity.Candidate;
import com.brightics.prj.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock,Long> {
    List<Stock> findStockByCandidateIs(Candidate candidate);
    Optional<Stock> findStockByCodeIs(String code);
}
