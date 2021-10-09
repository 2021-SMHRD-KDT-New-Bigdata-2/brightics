package com.brightics.prj.service;


import com.brightics.prj.entity.Candidate;
import com.brightics.prj.entity.Stock;
import com.brightics.prj.repository.DailyStockRepository;
import com.brightics.prj.repository.NewsRepository;
import com.brightics.prj.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {
    private final DailyStockRepository dailyStockRepository;
    private final NewsRepository newsRepository;
    private final StockRepository stockRepository;

    private static final DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ISO_DATE;


    public List<Object[]> makeCandidateNewsGraph(String code, String start, String end){
        Stock stock=stockRepository.findStockByCodeIs(code).stream().findAny().orElse(null);
        if(stock==null){
            return null;
        }
        LocalDate target= parsing(end);
        Long interval= DateBetween(parsing(start),parsing(end))-1L;
        Candidate candidate=stock.getCandidate();

        return newsRepository.findCountNumberOfNewsPerPeriodAndCandidateIs(target,interval,candidate.getId());

    }

    private static LocalDate parsing(String time){
        return LocalDate.parse(time,dateTimeFormatter);
    }

    private static Long DateBetween(LocalDate start, LocalDate end){
        return  ChronoUnit.DAYS.between(start, end);
    }


}
