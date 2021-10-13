package com.brightics.prj.member.entity;

import com.brightics.prj.web.entity.Stock;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Comment {

    @Id @GeneratedValue
    private Long id;
    private String Comment;

    @ManyToOne
    @JoinColumn(name="stock_code")
    private Stock stock;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @CreatedDate
    private LocalDateTime commentedAt;


}
