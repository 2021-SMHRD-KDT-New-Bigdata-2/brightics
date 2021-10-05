package com.brightics.prj.entity;


import javax.persistence.*;

@Entity
public class Stock {

    @Id
    @Column(name="stock_code")
    private String code;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="candidate_id")
    private Candidate candidate;
}
