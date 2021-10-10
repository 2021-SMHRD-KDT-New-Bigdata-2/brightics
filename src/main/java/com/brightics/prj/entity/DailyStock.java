package com.brightics.prj.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
public class DailyStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="dailystock_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="stock_code")
    private Stock stock;
    private Long tradingVolume;
    private LocalDateTime date;
    private double open;
    private double close;
    private double high;
    private double low;
    private String description;

    public DailyStock() {

    }
}
