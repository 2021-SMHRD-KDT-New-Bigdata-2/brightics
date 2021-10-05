package com.brightics.prj.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NamedEntityGraph(name="dailyStockWithStock",attributeNodes = @NamedAttributeNode("stock"))
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
