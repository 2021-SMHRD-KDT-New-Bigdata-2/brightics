package com.brightics.prj.member.entity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String loginId;
    private String email;
    private String password;
    private String profileImage;
    private String emailCheckToken;
    private Boolean emailVerified;



    public void genToken(){
        this.emailCheckToken = UUID.randomUUID().toString();
    }



}
