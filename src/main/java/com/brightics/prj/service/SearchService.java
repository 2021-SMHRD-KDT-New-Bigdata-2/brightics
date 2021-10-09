package com.brightics.prj.service;

import com.brightics.prj.entity.Stock;
import com.brightics.prj.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final StockRepository stockRepository;

    public List<Stock> stockSearch(){
        return stockRepository.findAll();
    }

}
