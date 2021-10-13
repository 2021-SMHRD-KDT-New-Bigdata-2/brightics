package com.brightics.prj.member.controller;

import com.brightics.prj.member.LoginForm;
import com.brightics.prj.member.SignupForm;
import com.brightics.prj.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String loginPage(@ModelAttribute(name="loginForm") LoginForm loginForm){
        return "login";
    }
    @GetMapping("/signup")
    public String signupPage(@ModelAttribute(name="signupForm") SignupForm signupForm){
        return "signup";
    }

    @PostMapping("/login")
    public String login(){
        return null;
    }

    @PostMapping("/signup")
    public String signup(@Valid SignupForm signupForm, Errors errors){
        if(errors.hasErrors()){
            return "signup";
        }
        memberService.signup(signupForm);
        return "redirect:/";

    }
}
