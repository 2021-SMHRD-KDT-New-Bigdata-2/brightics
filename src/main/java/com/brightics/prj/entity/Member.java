package com.brightics.prj.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Member {
    @Id
    @GeneratedValue
    @Column(name="member_id")
    private Long id;
    private String loginId;
    private String password;
    private String email;

}
