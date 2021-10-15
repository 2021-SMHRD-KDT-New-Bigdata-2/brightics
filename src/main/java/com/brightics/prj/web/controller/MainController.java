package com.brightics.prj.web.controller;

import com.brightics.prj.web.entity.Candidate;
import com.brightics.prj.web.entity.Comment;
import com.brightics.prj.web.entity.Member;
import com.brightics.prj.web.entity.Stock;
import com.brightics.prj.web.repository.CandidateRepository;
import com.brightics.prj.web.repository.CommentRepository;
import com.brightics.prj.web.repository.MemberRepository;
import com.brightics.prj.web.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final CandidateRepository candidateRepository;
    private final StockRepository stockRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;





    @GetMapping("")
    public String home(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);



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
    public String StockDetail(@PathVariable String code, Model model, Comment comment){
        Stock stock = stockRepository.findStockByCodeIs(code).get();
        model.addAttribute("stock", stock);
        model.addAttribute("candidateName", stock.getCandidate().getName());
        model.addAttribute("comment", comment);
        return "candidate/stock/stock-detail";
    }

    @PostMapping("/candidate/stock/{code}")
    public String createComment(@PathVariable String code, Model model, @Valid @ModelAttribute Comment comment, Errors errors){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member;
        if(authentication.getPrincipal().getClass()== DefaultOAuth2User.class){
            DefaultOAuth2User user= (DefaultOAuth2User) authentication.getPrincipal();
            Map att= user.getAttributes();
            String oauthId= att.get("id").toString();
            member=memberRepository.findMemberByOauthId(oauthId);
        }
        else {
            member=memberRepository.findMemberByLoginId(authentication.getPrincipal().toString()).stream().findAny().orElse(null);
        }
        Stock stock= stockRepository.findStockByCodeIs(code).stream().findAny().orElse(null);
        if (stock==null || member ==null){
            errors.reject("invalid");
        }
        if(errors.hasErrors()){
            return "redirect:/error";
        }

        comment.setMember(member);
        comment.setStock(stock);
        comment.setCommentedAt(LocalDateTime.now());
        commentRepository.save(comment);

        return "redirect:/candidate/stock/{code}";
    }






}


