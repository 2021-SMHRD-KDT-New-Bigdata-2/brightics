package com.brightics.prj.member;

import com.brightics.prj.member.entity.Member;
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
