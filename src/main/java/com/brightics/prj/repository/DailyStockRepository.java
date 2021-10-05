package com.brightics.prj.repository;


import com.brightics.prj.entity.DailyStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyStockRepository extends JpaRepository<DailyStock,Long> {
}
