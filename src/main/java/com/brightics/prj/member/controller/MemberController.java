package com.brightics.prj.member.controller;

import com.brightics.prj.member.LoginForm;
import com.brightics.prj.member.SignupForm;
import com.brightics.prj.member.SignupFormValidator;
import com.brightics.prj.member.entity.Member;
import com.brightics.prj.member.repository.MemberRepository;
import com.brightics.prj.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final SignupFormValidator signupFormValidator;


    @InitBinder("signupForm")
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(signupFormValidator);
    }


    @GetMapping("/login")
    public String loginPage(@ModelAttribute(name="loginForm") LoginForm loginForm){
        return "login";
    }
    @GetMapping("/signup")
    public String signupPage(@ModelAttribute(name="signupForm") SignupForm signupForm){
        return "signup";
    }

    @PostMapping("/login")
    public void login(Member member){


    }

    @PostMapping("/signup")
    public String signup(@Valid SignupForm signupForm, Errors errors){
        if(errors.hasErrors()){
            return "signup";
        }
        Member signupMember = memberService.signup(signupForm);
        return "redirect:/";

    }

    @GetMapping("/check-email-token")
    public String loginPage(String token, String email, Model model){
        Member member= memberRepository.findMemberByEmail(email);
        if (member==null){
            model.addAttribute("error","invalid.email");
            return "/checked-email";
        }
        if (!member.getEmailCheckToken().equals(token)){
            model.addAttribute("error","invalid.token");
            return "/checked-email";
        }

        member.setEmailVerified(true);
        model.addAttribute("memberEmail",member.getEmail());
        memberService.login(member);

        return "/checked-email";
    }

}

