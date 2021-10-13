package com.brightics.prj.member.entity;

import com.brightics.prj.entity.Stock;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Comment {

    @Id @GeneratedValue
    private Long id;
    private String Comment;

    @ManyToOne
    private Stock stock;

    @ManyToOne
    private Member member;

    @CreatedDate
    private LocalDateTime commentedAt;


}
