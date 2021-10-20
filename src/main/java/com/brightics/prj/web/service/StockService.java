package com.brightics.prj.web.service;


import com.brightics.prj.web.entity.Candidate;
import com.brightics.prj.web.entity.Stock;
import com.brightics.prj.web.repository.DailyStockRepository;
import com.brightics.prj.web.repository.NewsRepository;
import com.brightics.prj.web.repository.StockRepository;
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


    public List<Object[]> makeNewsGraph(String code, String start, String end){
        Stock stock=stockRepository.findStockByCodeIs(code).stream().findAny().orElse(null);
        if(stock==null){
            return null;
        }
        LocalDate target= parsing(end);
        Long interval= DateBetween(parsing(start),parsing(end));
        Candidate candidate=stock.getCandidate();

        return newsRepository.findCountNumberOfNewsPerPeriodAndCandidateIs(target,interval,candidate.getId());

    }
    public List<Object[]> makeStockGraph(String code, String start, String end){
        Stock stock=stockRepository.findStockByCodeIs(code).stream().findAny().orElse(null);
        if(stock==null){
            return null;
        }
        LocalDate startDate= parsing(start);
        LocalDate endDate= parsing(end);
//        Long interval= DateBetween(parsing(start),parsing(end));


        return dailyStockRepository.findStockInfoPerDayNoWeekend(stock.getCode(), startDate, endDate);
    }







    private static LocalDate parsing(String time){
        return LocalDate.parse(time,dateTimeFormatter);
    }

    private static Long DateBetween(LocalDate start, LocalDate end){
        return  ChronoUnit.DAYS.between(start, end);
    }


}
