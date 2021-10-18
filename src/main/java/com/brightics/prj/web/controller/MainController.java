package com.brightics.prj.web.controller;

import com.brightics.prj.web.entity.*;
import com.brightics.prj.web.form.CommentForm;
import com.brightics.prj.web.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Slf4j
public class MainController {
    private final CandidateRepository candidateRepository;
    private final StockRepository stockRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final NoticeRepository noticeRepository;





    @GetMapping("")
    public String home(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

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
    public String StockDetail(@PathVariable String code, Model model, CommentForm commentForm,@PageableDefault(size = 10) Pageable pageable){
        Stock stock = stockRepository.findStockByCodeIs(code).get();
        Page<Comment> commentList=commentRepository.findCommentByStockIs(stock, pageable);
        model.addAttribute("stock", stock);
        model.addAttribute("candidateName", stock.getCandidate().getName());
        model.addAttribute("commentForm", commentForm);
        model.addAttribute("commentList", commentList);
        return "candidate/stock/stock-detail";
    }

    @PostMapping("/candidate/stock/{code}")
    public String createComment(@PathVariable String code, Model model, @Valid @ModelAttribute CommentForm commentForm, Errors errors){

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
        Comment comment=new Comment();
        comment.setComment(commentForm.getComment());
        comment.setMember(member);
        comment.setStock(stock);
        comment.setCommentedAt(LocalDateTime.now());
        commentRepository.save(comment);

        return "redirect:/candidate/stock/{code}";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/candidate/stock/{code}/delete/{id}")
    public String deleteComment(@PathVariable Long id, @PathVariable String code){
        log.error("here");
        Comment comment= commentRepository.findById(id).stream().findAny().orElse(null);
        if (comment==null){
            return "redirect:/error";
        }
        commentRepository.delete(comment);
        return "redirect:/candidate/stock/{code}";
    }

    @GetMapping("/search")
    public String search () {
        return "search";
    }

    @GetMapping("/notice")
    public String noticePage(Model model, Pageable pageable){

        Page<Notice> noticeList=noticeRepository.findAll(pageable);
        model.addAttribute("noticeList", noticeList);
        return "notice";
    }
    @GetMapping("/notice/{id}")
    public String noticeDetail(@PathVariable Long id, Model model){
        Notice notice = noticeRepository.findById(id).stream().findAny().orElse(null);
        model.addAttribute("notice", notice);


        return "noticepage";
    }

}


