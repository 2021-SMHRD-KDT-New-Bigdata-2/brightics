package com.brightics.prj.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class News {
    @Id @GeneratedValue
    @Column(name="news_id")
    private Long id;
    private String title;
    private String contents;
    private String url;
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="candidate_id")
    private Candidate candidate;


}
