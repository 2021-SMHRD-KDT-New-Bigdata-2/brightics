package com.brightics.prj.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class News {
    @Id @GeneratedValue
    @Column(name="news_id")
    private Long id;
    private String title;
    private String contents;
    private String url;
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="candidate_id")
    private Candidate candidate;


}
