package com.brightics.prj.member.service;

import com.brightics.prj.web.form.LoginForm;
import com.brightics.prj.web.form.SignupForm;
import com.brightics.prj.web.entity.Member;
import com.brightics.prj.web.repository.CommentRepository;
import com.brightics.prj.web.repository.MemberRepository;
import com.brightics.prj.web.repository.StockRepository;
import com.brightics.prj.web.service.MemberService;
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
    void signup() {
        SignupForm signupForm= new SignupForm();
        signupForm.setLoginId("임재곤");
        signupForm.setPassword("1234");
        signupForm.setEmail("im@gmail.com");

        memberService.signup(signupForm);

        Optional<Member> findMember = memberRepository.findMemberByLoginId("임재곤");

        Assertions.assertThat(findMember.get().getPassword()).isNotEqualTo("1234");


    }
    @Test
    void login(){
        SignupForm signupForm= new SignupForm();
        signupForm.setLoginId("임재곤");
        signupForm.setPassword("1234");
        signupForm.setEmail("im@gmail.com");
        memberService.signup(signupForm);

        LoginForm loginForm=new LoginForm();
        loginForm.setLoginId("임재곤");
        loginForm.setPassword("1234");



    }

    @Test
    void comment(){
    }
}