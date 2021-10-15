package com.brightics.prj.web.controller;

import com.brightics.prj.web.ForgotPasswordForm;
import com.brightics.prj.web.LoginForm;
import com.brightics.prj.web.SignupForm;
import com.brightics.prj.web.SignupFormValidator;
import com.brightics.prj.web.entity.Member;
import com.brightics.prj.web.repository.MemberRepository;
import com.brightics.prj.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

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
    public String loginPage(@ModelAttribute(name="loginForm") LoginForm loginForm ,@RequestParam(value = "error" ,required=false) String error ,@RequestParam(value = "exception" ,required=false) String exception, Model model){
        model.addAttribute("error",error);
        model.addAttribute("exception",exception);

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


    @Transactional
    @GetMapping("/check-email-token")
    public String loginPage(String token, String email, Model model){
        Member member= memberRepository.findMemberByEmail(email);
        if (member==null){
            model.addAttribute("error","invalid.email");
            return "/checked-email";
        }
        if(member.getEmailCheckToken().equals("checked")){
            model.addAttribute("error","invalid.token");
        }
        if (!member.getEmailCheckToken().equals(token)){
            model.addAttribute("error","invalid.token");
            return "/checked-email";
        }


        member.setEmailVerified(true);
        member.setEmailCheckToken("checked");
        model.addAttribute("memberEmail",member.getEmail());
        memberService.login(member);

        return "/checked-email";
    }
    @GetMapping("/we-send-email")
    public String weSendEmail(){
        return "we-send-email";
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage(Model model){
        model.addAttribute("forgotPasswordForm", new ForgotPasswordForm());
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@Valid ForgotPasswordForm forgotPasswordForm, Errors errors){
        if(errors.hasErrors()){
            return "redirect:/forgot-password";
        }
        memberService.findMemberAccount(forgotPasswordForm);
        return "redirect:/we-send-email";
    }


}

