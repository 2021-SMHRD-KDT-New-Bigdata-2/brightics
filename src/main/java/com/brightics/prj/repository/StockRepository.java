package com.brightics.prj.repository;

import com.brightics.prj.entity.Candidate;
import com.brightics.prj.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock,Long> {
    List<Stock> findStockByCandidateIs(Candidate candidate);
}
