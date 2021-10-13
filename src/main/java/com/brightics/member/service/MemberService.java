package com.brightics.member.service;

import com.brightics.member.RegisterForm;
import com.brightics.member.entity.Member;
import org.springframework.stereotype.Service;

@Service
public class MemberService {



    public Member register(RegisterForm registerForm){
        Member member = new Member();
        member.setLoginId(registerForm.getLoginId());





        return member;
    }
}
