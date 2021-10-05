package com.brightics.prj.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@SequenceGenerator(
        name = "news_id_generator",
        sequenceName = "news_id", // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 1,
        allocationSize = 1)
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
