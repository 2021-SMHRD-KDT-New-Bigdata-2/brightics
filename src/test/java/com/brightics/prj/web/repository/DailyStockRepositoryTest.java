package com.brightics.prj.web.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;

@SpringBootTest
@Transactional
class DailyStockRepositoryTest {

    @Autowired
    private DailyStockRepository dailyStockRepository;

    @Test
    void test() {
        dailyStockRepository.findStockInfoPerDay(LocalDate.now(),7L,"025550");
    }
}