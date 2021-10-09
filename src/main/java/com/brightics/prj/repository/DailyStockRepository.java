package com.brightics.prj.repository;


import com.brightics.prj.entity.DailyStock;
import com.brightics.prj.entity.Stock;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DailyStockRepository extends JpaRepository<DailyStock, String> {
    @Query(value = "with recursive all_dates as (\n" +
            "                select DATE_SUB(date(:target), INTERVAL :interval day) dt\n" +
            "                    union all\n" +
            "                select dt + interval 1 day from all_dates where dt + interval 1 day <= DATE(date(:target))\n" +
            "            )\n" +
            "            select DATE_FORMAT(dt, '%Y-%m-%d') as md, IFNULL(sum(ds.low),0) ,IFNULL(sum(ds.open),0) ,IFNULL(sum(ds.close),0) ,IFNULL(sum(ds.high),0) \n" +
            "            FROM all_dates as ad \n" +
            "            LEFT JOIN daily_stock ds \n" +
            "            ON DATE_FORMAT(ad.dt, '%Y-%m-%d') = DATE_FORMAT(ds.date, '%Y-%m-%d') \n" +
            "            AND ds.stock_code= :stock_code \n" +
            "            GROUP BY `md` \n" +
            "            ORDER BY `md` ", nativeQuery = true) // 각 날짜별 등록된 - 후보별 뉴스의 카운트 구하는 쿼리
    public List<Object[]> findStockInfoPerDay(@Param("target") LocalDate target,
                                              @Param("interval") Long interval,
                                              @Param("stock_code") String stock_code
    );
}
