package com.brightics.prj.web.repository;

import com.brightics.prj.web.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News,Long> {
    @Query(value = "with recursive all_dates(dt) as (\n" +
            "    select DATE_SUB(date(:target), INTERVAL :interval day) dt\n" +
            "        union all\n" +
            "    select dt + interval 1 day from all_dates where dt + interval 1 day <= DATE(date(:target))\n" +
            ")\n" +
            "select DATE_FORMAT(dt, '%Y-%m-%d') as md, IFNULL(count(n.news_id),0)" +
            "FROM all_dates as ad\n" +
            "LEFT JOIN news n\n" +
            "ON DATE_FORMAT(ad.dt, '%Y-%m-%d') = DATE_FORMAT(n.date, '%Y-%m-%d')\n" +
            "GROUP BY `md`\n" +
            "ORDER BY `md` ", nativeQuery = true) // 각 날짜별 등록된 뉴스의 카운트 구하는 쿼리
    List<Object[]> findCountNumberOfNewsPerPeriod(@Param("target") LocalDateTime target,
                                                  @Param("interval") Long interval);
    // target : 타겟 날짜, interval : 타겟 날짜에서 뺄 일수, target-interval ~ target 날짜까지 나옴.

    @Query(value = "with recursive all_dates(dt) as (\n" +
            "    select DATE_SUB(date(:target), INTERVAL :interval day) dt\n" +
            "        union all\n" +
            "    select dt + interval 1 day from all_dates where dt + interval 1 day <= DATE(date(:target))\n" +
            ")\n" +
            "select DATE_FORMAT(dt, '%Y-%m-%d') as md, IFNULL(count(n.news_id),0)\n" +
            "FROM all_dates as ad\n" +
            "LEFT JOIN news n\n" +
            "ON DATE_FORMAT(ad.dt, '%Y-%m-%d') = DATE_FORMAT(n.date, '%Y-%m-%d')\n" +
            "AND n.candidate_id = :candidate_id \n" +
            "GROUP BY `md`\n" +
            "ORDER BY `md` ", nativeQuery = true) // 각 날짜별 등록된 - 후보별 뉴스의 카운트 구하는 쿼리
    List<Object[]> findCountNumberOfNewsPerPeriodAndCandidateIs(@Param("target") LocalDate target,
                                                                @Param("interval") Long interval,
                                                                @Param("candidate_id") Long candidate_id);

}
