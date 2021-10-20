package com.brightics.prj.web.apicontroller;

import com.brightics.prj.web.repository.StockRepository;
import com.brightics.prj.web.service.SearchService;
import com.brightics.prj.web.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;


@RestController
@RequiredArgsConstructor
@Slf4j
public class StockApiController {
    private final StockService stockService;
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
}
