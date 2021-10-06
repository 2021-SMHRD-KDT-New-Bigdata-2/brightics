package com.brightics.prj.controller;

import com.brightics.prj.entity.Candidate;
import com.brightics.prj.entity.Stock;
import com.brightics.prj.repository.CandidateRepository;
import com.brightics.prj.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final CandidateRepository candidateRepository;
    private final StockRepository stockRepository;


    @GetMapping("")
    public String home(){
        System.out.println("11");
        return "index";
    }
    @GetMapping("/candidate")
    public String candidate(Model model){
        List<Candidate> candidates=candidateRepository.findAll();
        model.addAttribute("candidates",candidates);
        return "candidate";
    }
    @GetMapping("/candidate/{id}")
    public String candidateDetail(@PathVariable Long id, Model model){
        Candidate candidate=candidateRepository.findById(id).get();
        List<Stock> stockList=stockRepository.findStockByCandidateIs(candidate);

        model.addAttribute("candidate",candidate);
        model.addAttribute("stockList",stockList);

        return "candidate/candidate-detail";
    }
//    @GetMapping("/candidate/detail/detail")
//    public String StockDetail(){
//        return "stock-detail";
//    }
}


