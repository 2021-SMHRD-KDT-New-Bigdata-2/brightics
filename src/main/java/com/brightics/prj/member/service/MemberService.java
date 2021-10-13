package com.brightics.prj.member.service;

import com.brightics.prj.member.RegisterForm;
import com.brightics.prj.member.entity.Member;
import com.brightics.prj.member.entity.MemberRole;
import com.brightics.prj.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member register(RegisterForm registerForm){
        Member member = new Member();
        member.setLoginId(registerForm.getLoginId());

        String hashedPassword = BCrypt.hashpw(registerForm.getPassword(),BCrypt.gensalt());
        member.setPassword(hashedPassword);

        member.setEmail(registerForm.getEmail());
        member.setMemberRole(MemberRole.user);

        memberRepository.save(member);


        return member;
    }
}
