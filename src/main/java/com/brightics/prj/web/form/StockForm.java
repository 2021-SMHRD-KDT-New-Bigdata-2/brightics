package com.brightics.prj.web.form;

import com.brightics.prj.web.entity.Candidate;
import lombok.Data;

@Data
public class StockForm {
    private String stock_code;
    private String name;
    private String category;
    private String description;
    private Candidate candidate;
}

