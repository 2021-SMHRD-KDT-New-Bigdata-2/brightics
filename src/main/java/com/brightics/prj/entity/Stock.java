package com.brightics.prj.entity;


import javax.persistence.*;

@Entity
public class Stock {

    @Id
    @GeneratedValue
    @Column(name="stock_id")
    private Long id;
    private String code;


    @ManyToOne
    @JoinColumn(name="candidate_id")
    private Candidate candidate;
}
