package com.brightics.prj.member.service;

import com.brightics.prj.member.RegisterForm;
import com.brightics.prj.member.entity.Member;
import com.brightics.prj.member.entity.MemberRole;
import com.brightics.prj.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Optional;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;




    @Test
    void register() {
        RegisterForm registerForm= new RegisterForm();
        registerForm.setLoginId("임재곤");
        registerForm.setPassword("1234");
        registerForm.setEmail("im@gmail.com");

        memberService.register(registerForm);

        Optional<Member> findMember = memberRepository.findMemberByLoginId("임재곤");

        Assertions.assertThat(findMember.get().getPassword()).isNotEqualTo("1234");
        Assertions.assertThat(findMember.get().getMemberRole()).isEqualTo(MemberRole.user);

    }
}