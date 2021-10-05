package com.brightics.prj.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Stock {

    @Id
    @Column(name="stock_code")
    private String code;
    private String name;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="candidate_id")
    private Candidate candidate;
}
