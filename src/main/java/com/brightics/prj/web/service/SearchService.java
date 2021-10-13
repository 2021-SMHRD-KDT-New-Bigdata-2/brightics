package com.brightics.prj.web.service;

import com.brightics.prj.web.entity.Stock;
import com.brightics.prj.web.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final StockRepository stockRepository;

    public List<Stock> stockSearch(){
        return stockRepository.findAll();
    }

}
