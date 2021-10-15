package com.brightics.prj.web.controller;

import com.brightics.prj.web.entity.Candidate;
import com.brightics.prj.web.entity.Stock;
import com.brightics.prj.web.repository.CandidateRepository;
import com.brightics.prj.web.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final CandidateRepository candidateRepository;
    private final StockRepository stockRepository;





    @GetMapping("")
    public String home(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getPrincipal().getClass());



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
    @GetMapping("/candidate/stock/{code}")
    public String StockDetail(@PathVariable String code, Model model){
        Stock stock = stockRepository.findStockByCodeIs(code).get();
        model.addAttribute("stock", stock);
        model.addAttribute("candidateName", stock.getCandidate().getName());
        return "candidate/stock/stock-detail";
    }






}


