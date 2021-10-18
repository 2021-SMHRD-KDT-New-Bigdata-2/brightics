package com.brightics.prj.web.controller;

import com.brightics.prj.web.entity.Candidate;

import com.brightics.prj.web.form.ForgotPasswordForm;
import com.brightics.prj.web.form.LoginForm;
import com.brightics.prj.web.form.SignupForm;
import com.brightics.prj.web.repository.CandidateRepository;
import com.brightics.prj.web.util.SignupFormValidator;
import com.brightics.prj.web.entity.Member;
import com.brightics.prj.web.repository.MemberRepository;
import com.brightics.prj.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final SignupFormValidator signupFormValidator;
    private final PasswordEncoder passwordEncoder;


    @InitBinder("signupForm")
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(signupFormValidator);
    }


    @GetMapping("/login")
    public String loginPage(@ModelAttribute(name="loginForm") LoginForm loginForm ,@RequestParam(value = "error" ,required=false) String error ,@RequestParam(value = "exception" ,required=false) String exception, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object authority=authentication.getAuthorities().stream().filter(
                a->a.equals(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))).findAny().orElse(null);
        if(authority==null){
            return "redirect:/";
        }

        model.addAttribute("error",error);
        model.addAttribute("exception",exception);

        return "member/login";
    }
    @GetMapping("/signup")
    public String signupPage(@ModelAttribute(name="signupForm") SignupForm signupForm){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object authority=authentication.getAuthorities().stream().filter(
                a->a.equals(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))).findAny().orElse(null);
        if(authority==null){
            return "redirect:/";
        }

        return "member/signup";
    }

    @PostMapping("/login")
    public void login(Member member){


    }

    @PostMapping("/signup")
    public String signup(@Valid SignupForm signupForm, Errors errors){
        if(errors.hasErrors()){
            return "member/signup";
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
            return "member/checked-email";
        }
        if(member.getEmailCheckToken().equals("checked")){
            model.addAttribute("error","invalid.token");
        }
        if (!member.getEmailCheckToken().equals(token)){
            model.addAttribute("error","invalid.token");
            return "member/checked-email";
        }


        member.setEmailVerified(true);
        member.setEmailCheckToken("checked");
        model.addAttribute("memberEmail",member.getEmail());
        memberService.login(member);

        return "member/checked-email";
    }
    @GetMapping("/we-send-email")
    public String weSendEmail(){
        return "we-send-email";
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage(Model model){
        model.addAttribute("forgotPasswordForm", new ForgotPasswordForm());
        return "member/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@Valid ForgotPasswordForm forgotPasswordForm, Errors errors){
        if(errors.hasErrors()){
            return "redirect:/forgot-password";
        }
        memberService.findMemberAccount(forgotPasswordForm);
        return "redirect:/we-send-email";
    }

    @GetMapping("/mypage")
    public String myPageHome(){
        Member member=getMember();
        if (member==null){
            return "redirect:/";
        }
        Long id=member.getId();

        return "redirect:/mypage/"+id;
    }
    @GetMapping("/mypage/{id}")
    public String myPageDetail(Model model, @PathVariable Long id){
        Member member=getMember();
        if (member==null){
            return "redirect:/";
        }
        model.addAttribute("member", member);

        return "member/mypage";
    }
    @PostMapping("/mypage/{id}")
    public String changePassword(Model model, @PathVariable Long id){
        Member member=getMember();
        if (member==null){
            return "redirect:/";
        }


        return "member/mypage";
    }




    @PostMapping("/mypage/add-candidate")
    public String addCandidate(){
        return null;
    }


    private Member getMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member;
        if(authentication.getPrincipal().getClass()== DefaultOAuth2User.class){
            DefaultOAuth2User user= (DefaultOAuth2User) authentication.getPrincipal();
            Map att= user.getAttributes();
            String oauthId= att.get("id").toString();
            member=memberRepository.findMemberByOauthId(oauthId);
        }
        else {
            member=memberRepository.findMemberByLoginId(authentication.getName()).stream().findAny().orElse(null);
        }
        return member;
    }
}

