package com.brightics.prj.web;

import com.brightics.prj.web.entity.Member;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class UserAccount extends User {
    private Member member;

    public UserAccount(Member member) {
        super(member.getLoginId(), member.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.member=member;
    }


}
