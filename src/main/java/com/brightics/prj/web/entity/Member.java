package com.brightics.prj.web.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String oauthId;
    private String kakaoEmail;

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
