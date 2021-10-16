package com.brightics.prj.web.form;

import lombok.Data;

@Data
public class StockForm {
    private String stock_code;
    private String name;
    private String category;
    private String description;
    private Long candidate_id;
}

