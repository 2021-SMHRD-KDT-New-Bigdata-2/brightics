package com.brightics.prj.web.form;

import com.brightics.prj.web.entity.Candidate;
import com.brightics.prj.web.entity.News;
import com.brightics.prj.web.entity.Stock;
import org.springframework.data.domain.Page;

import java.util.List;

public class MyPageDto {

    private Candidate candidate;
    private Long countNewsPercent;
    private Page<News> recentNewsList;
    private Long averagePercent;
    private Stock recentBestStock;
    
}
