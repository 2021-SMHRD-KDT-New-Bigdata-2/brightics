package com.brightics.prj.web.controller;

import com.brightics.prj.web.entity.Candidate;
import com.brightics.prj.web.entity.Notice;
import com.brightics.prj.web.entity.Stock;
import com.brightics.prj.web.form.NoticeForm;
import com.brightics.prj.web.form.StockForm;
import com.brightics.prj.web.repository.CandidateRepository;
import com.brightics.prj.web.repository.NoticeRepository;
import com.brightics.prj.web.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AdminController {
    private final CandidateRepository candidateRepository;
    private final StockRepository stockRepository;
    private final NoticeRepository noticeRepository;

    @GetMapping("/admin")
    public String adminHome(Model model){
        model.addAttribute("stockForm", new StockForm());
        List<Candidate> candidateList= candidateRepository.findAll();
        model.addAttribute("candidateList",candidateList);
        model.addAttribute("noticeForm",new NoticeForm());

        return "admin";
    }
    @PostMapping("/admin")
    public String addStock(@ModelAttribute StockForm stockForm){
        log.info("123");
        log.info(stockForm.getCandidate().getName());
        Stock stock=new Stock();
        stock.setCandidate(stockForm.getCandidate());
        stock.setCode(stockForm.getStock_code());
        stock.setCategory(stockForm.getCategory());
        stock.setDescription(stockForm.getDescription());
        stock.setName(stockForm.getName());
        stockRepository.save(stock);
        return "redirect:/admin";
    }

    @PostMapping("/notice")
    public String addNotice(@ModelAttribute NoticeForm noticeForm) {
        Notice notice=new Notice();
        notice.setTitle(noticeForm.getTitle());
        notice.setContent(noticeForm.getContent());
        notice.setNoticedAt(LocalDateTime.now());
        noticeRepository.save(notice);
        return "redirect:/admin";
    }


    @PostMapping("/notice/{id}")
    public String deleteNotice(@PathVariable Long id){
        Notice notice= noticeRepository.findById(id).stream().findAny().orElse(null);
        if (notice!=null){
            noticeRepository.delete(notice);
        }
        return "redirect:/notice";
    }



}
