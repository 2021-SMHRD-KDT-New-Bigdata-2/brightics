package com.brightics.prj.web.apicontroller;


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
    private final StockRepository stockRepository;


    @GetMapping("/searchApi")
    public List<Stock> searchStock () {
        List<Stock> stockList = stockRepository.findAll();

        return stockList;
    }

}
