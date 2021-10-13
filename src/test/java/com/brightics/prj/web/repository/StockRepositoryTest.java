package com.brightics.prj.web.repository;

import com.brightics.prj.web.entity.Stock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
public class StockRepositoryTest {
    @Autowired
    StockRepository stockRepository;

    @Test
    void test(){
        Optional<Stock> stockByCodeIs = stockRepository.findStockByCodeIs("13360");
        System.out.println(stockByCodeIs.get().getCandidate().getName());
    }
}
