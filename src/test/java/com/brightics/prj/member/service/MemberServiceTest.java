package com.brightics.prj.member.service;

import com.brightics.prj.entity.Stock;
import com.brightics.prj.member.LoginForm;
import com.brightics.prj.member.RegisterForm;
import com.brightics.prj.member.entity.Member;
import com.brightics.prj.member.entity.MemberRole;
import com.brightics.prj.member.repository.CommentRepository;
import com.brightics.prj.member.repository.MemberRepository;
import com.brightics.prj.repository.StockRepository;
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

    @Autowired
    StockRepository stockRepository;
    @Autowired
    CommentRepository commentRepository;






    @Test
    void register() {
        RegisterForm registerForm= new RegisterForm();
        registerForm.setLoginId("임재곤");
        registerForm.setPassword("1234");
        registerForm.setEmail("im@gmail.com");

        memberService.register(registerForm);

        Optional<Member> findMember = memberRepository.findMemberByLoginId("임재곤");

        Assertions.assertThat(findMember.get().getPassword()).isNotEqualTo("1234");
        Assertions.assertThat(findMember.get().getMemberRole()).isEqualTo(MemberRole.USER);

    }
    @Test
    void login(){
        RegisterForm registerForm= new RegisterForm();
        registerForm.setLoginId("임재곤");
        registerForm.setPassword("1234");
        registerForm.setEmail("im@gmail.com");
        memberService.register(registerForm);

        LoginForm loginForm=new LoginForm();
        loginForm.setLoginId("임재곤");
        loginForm.setPassword("1234");

        Member loginMember= memberService.login(loginForm);
        System.out.println(loginMember.getLoginId());


    }

    @Test
    void comment(){
        RegisterForm registerForm= new RegisterForm();
        registerForm.setLoginId("임재곤");
        registerForm.setPassword("1234");
        registerForm.setEmail("im@gmail.com");
        Member member= memberService.register(registerForm);
        Stock stock= stockRepository.findAll().get(0);

        memberService.CreateComment("ㅎㅎ", member, stock);


    }
}