package com.brightics.prj.web.controller;

import com.brightics.prj.member.LoginForm;
import com.brightics.prj.member.MailSender;
import com.brightics.prj.member.SignupForm;
import com.brightics.prj.member.SignupFormValidator;
import com.brightics.prj.member.entity.Member;
import com.brightics.prj.member.service.MemberService;
import com.brightics.prj.web.entity.Candidate;
import com.brightics.prj.web.entity.Stock;
import com.brightics.prj.web.repository.CandidateRepository;
import com.brightics.prj.web.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final CandidateRepository candidateRepository;
    private final StockRepository stockRepository;
    private final SignupFormValidator signupFormValidator;
    private final MemberService memberService;


    @InitBinder("signupForm")
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(signupFormValidator);
    }


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






}


