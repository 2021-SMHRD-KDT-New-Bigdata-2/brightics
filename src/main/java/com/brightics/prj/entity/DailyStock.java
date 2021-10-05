package com.brightics.prj.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class DailyStock {
    @Id
    @GeneratedValue
    @Column(name="dailystock_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="stock_code")
    private Stock stock;
    private Long tradingVolume;
    private Date date;
    private double open;
    private double close;
    private double high;
    private double low;
}
