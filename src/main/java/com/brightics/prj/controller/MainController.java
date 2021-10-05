package com.brightics.prj.controller;

import com.brightics.prj.entity.Candidate;
import com.brightics.prj.repository.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {


    private final CandidateRepository candidateRepository;

    @GetMapping("")
    public String Home(Model model){
        List<Candidate> candidateList =candidateRepository.findAll();
        model.addAttribute("candidateList",candidateList);

        return "index";
    }
}


