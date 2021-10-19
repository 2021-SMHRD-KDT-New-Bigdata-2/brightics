package com.brightics.prj.web.apicontroller;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;

@RestController
public class Test {

    @GetMapping("/test")
    public Object test() throws IOException {
        String url="http://www.paxnet.co.kr/stock/analysis/main?abbrSymbol=269620";
         Document doc= Jsoup.connect(url).get();
        System.out.println(doc);

        return "11";


    }
}
