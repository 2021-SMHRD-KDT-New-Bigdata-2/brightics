package com.brightics.prj.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AnalysisController {
    @GetMapping("/analysis")
    public String analysisPage(){
        return "analysis/analysis";
    }

}
