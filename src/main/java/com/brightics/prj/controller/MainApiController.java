package com.brightics.prj.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainApiController {

    @GetMapping("/candidate/stock/{code}/getnewsgraph")
    public List<Object[]> newsGraphInfoApi(@PathVariable String code){
        return null;
    }





}
