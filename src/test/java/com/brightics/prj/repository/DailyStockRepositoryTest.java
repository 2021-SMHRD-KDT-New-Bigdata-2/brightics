package com.brightics.prj.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DailyStockRepositoryTest {

    @Autowired
    private DailyStockRepository dailyStockRepository;

    @Test
    void test() {
        dailyStockRepository.findStockInfoPerDay(LocalDate.now(),7,"025550");
    }
}