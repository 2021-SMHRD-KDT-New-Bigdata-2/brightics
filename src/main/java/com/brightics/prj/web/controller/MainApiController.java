package com.brightics.prj.web.controller;


import com.brightics.prj.web.entity.Stock;
import com.brightics.prj.web.repository.StockRepository;
import com.brightics.prj.web.service.SearchService;
import com.brightics.prj.web.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MainApiController {
    private final StockService stockService;
    private final SearchService searchService;
    private static final LocalDate CURRENT_DATE= LocalDate.now();
    private final StockRepository stockRepository;

    @GetMapping("/candidate/stock/{code}/getnewsgraph")
    public Object newsGraphInfoApi(@PathVariable String code , @RequestParam(required = false) String start, @RequestParam(required = false) String end){
        String endDate=CURRENT_DATE.toString();
        String startDate=CURRENT_DATE.minusDays(6).toString();
        if(start!=null && end!=null){
            startDate=start;
            endDate=end;
        }
        return stockService.makeNewsGraph(code,startDate,endDate);
    }
    @GetMapping("/candidate/stock/{code}/getstockgraph")
    public Object stockGraphInfoApi(@PathVariable String code , @RequestParam(required = false) String start, @RequestParam(required = false) String end){
        String endDate=CURRENT_DATE.toString();
        String startDate=CURRENT_DATE.minusDays(6).toString();
        if(start!=null && end!=null){
            startDate=start;
            endDate=end;
        }
        return stockService.makeStockGraph(code,startDate,endDate);
    }

    @GetMapping("/searchApi")
    public List<Stock> searchStock () {
        List<Stock> stockList = stockRepository.findAll();

        return stockList;
    }

}
