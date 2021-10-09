package com.brightics.prj.controller;


import com.brightics.prj.entity.Stock;
import com.brightics.prj.service.SearchService;
import com.brightics.prj.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainApiController {
    private final StockService stockService;
    private final SearchService searchService;
    private static final LocalDate CURRENT_DATE= LocalDate.now();


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
    @GetMapping("/search")
    public List<Stock> searchApi(){
        return searchService.stockSearch();
    }


}
