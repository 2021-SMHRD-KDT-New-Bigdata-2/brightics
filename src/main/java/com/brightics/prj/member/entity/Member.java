package com.brightics.prj.member.entity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String loginId;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)

    private MemberRole memberRole;
}
