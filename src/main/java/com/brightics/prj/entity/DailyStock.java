package com.brightics.prj.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@SequenceGenerator(
        name = "dailystock_id_generator",
        sequenceName = "dailystock_id", // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 1,
        allocationSize = 1)
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

    public DailyStock() {

    }
}
