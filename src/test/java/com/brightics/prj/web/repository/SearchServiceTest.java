package com.brightics.prj.web.repository;

import com.brightics.prj.web.entity.Stock;
import com.brightics.prj.web.service.SearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.regex.Pattern;

@SpringBootTest
public class SearchServiceTest {
    @Autowired
    StockRepository stockRepository;
    @Autowired
    SearchService searchService;
    @Test
    void test(){
        Optional<Stock> any = searchService.stockSearch().stream().findAny();
        System.out.println(any.get().getCandidate().getName());
    }

    @Test
    void validate(){
        String q="헣abc";
        boolean name_check = Pattern.matches("^[가-힣a-zA-z]*$", q);
        System.out.println(name_check);

    }
}
