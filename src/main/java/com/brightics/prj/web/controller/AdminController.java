package com.brightics.prj.web.controller;

import com.brightics.prj.web.entity.Candidate;
import com.brightics.prj.web.entity.Comment;
import com.brightics.prj.web.entity.Notice;
import com.brightics.prj.web.entity.Stock;
import com.brightics.prj.web.form.NoticeForm;
import com.brightics.prj.web.form.StockForm;
import com.brightics.prj.web.repository.CandidateRepository;
import com.brightics.prj.web.repository.CommentRepository;
import com.brightics.prj.web.repository.NoticeRepository;
import com.brightics.prj.web.repository.StockRepository;
import com.brightics.prj.web.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AdminController {
    private final CandidateRepository candidateRepository;
    private final AdminService adminService;
    private final NoticeRepository noticeRepository;
    private final CommentRepository commentRepository;

    @GetMapping("/admin")
    public String adminHome(Model model,@PageableDefault(sort = "commentedAt", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Comment> commentList = commentRepository.findAll(pageable);
        List<Candidate> candidateList= candidateRepository.findAll();

        model.addAttribute("stockForm", new StockForm());
        model.addAttribute("candidateList",candidateList);
        model.addAttribute("noticeForm",new NoticeForm());
        model.addAttribute("commentList", commentList);
        return "admin";
    }
    @PostMapping("/admin")
    public String addStock(@ModelAttribute StockForm stockForm){

        adminService.addStock(stockForm);
        return "redirect:/admin";
    }

    @PostMapping("/notice")
    public String addNotice(@ModelAttribute NoticeForm noticeForm) {

        adminService.addNotice(noticeForm);
        return "redirect:/admin";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/notice/{id}")
    public String deleteNotice(@PathVariable Long id, HttpServletRequest request){
        Notice notice= noticeRepository.findById(id).stream().findAny().orElse(null);
        String refer= request.getHeader("Referer");
        if (notice!=null){
            noticeRepository.delete(notice);
            return "redirect:"+refer;
        }

        return "redirect:/";
    }

}
