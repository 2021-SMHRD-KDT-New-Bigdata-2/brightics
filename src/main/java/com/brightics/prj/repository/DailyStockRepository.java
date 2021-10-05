package com.brightics.prj.repository;


import com.brightics.prj.entity.DailyStock;
import com.brightics.prj.entity.Stock;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface DailyStockRepository extends JpaRepository<DailyStock,String> {
    @EntityGraph("dailyStockWithStock")
    List<DailyStock> findDailyStockByDateBetweenAndStockIs(Date start, Date end, Stock stock);
}
