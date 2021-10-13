package com.brightics.prj.web.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class StockServiceTest {
    @Autowired
    StockService stockService;

    @Test
    void test1(){
        List<Object[]> resultList= stockService.makeNewsGraph("13360","2010-10-10","2010-10-11");
        for (Object[] objects : resultList) {
            System.out.println("objects = " + objects);
        }
    }


}