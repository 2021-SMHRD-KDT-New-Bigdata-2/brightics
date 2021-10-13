package com.brightics.prj.web.controller;

import com.brightics.prj.member.LoginForm;
import com.brightics.prj.member.SignupForm;
import com.brightics.prj.web.entity.Candidate;
import com.brightics.prj.web.entity.Stock;
import com.brightics.prj.web.repository.CandidateRepository;
import com.brightics.prj.web.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final CandidateRepository candidateRepository;
    private final StockRepository stockRepository;


    @GetMapping("")
    public String home(){

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

    @GetMapping("/login")
    public String loginPage(@ModelAttribute(name="loginForm")LoginForm loginForm){
        return "login";
    }
    @GetMapping("/signup")
    public String signupPage(@ModelAttribute(name="signupForm") SignupForm signupForm){
        return "signup";
    }

}


