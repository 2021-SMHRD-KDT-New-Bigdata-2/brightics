package com.brightics.prj.web.service;

import com.brightics.prj.web.entity.*;
import com.brightics.prj.web.form.CommentForm;
import com.brightics.prj.web.form.ForgotPasswordForm;
import com.brightics.prj.web.form.UserAccount;
import com.brightics.prj.web.form.SignupForm;
import com.brightics.prj.web.repository.CommentRepository;
import com.brightics.prj.web.repository.MemberRepository;
import com.brightics.prj.web.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;
    private final CommentRepository commentRepository;




    private Member saveMember(SignupForm signupForm){
        Member member = new Member();
        member.setLoginId(signupForm.getLoginId());
        member.setPassword(passwordEncoder.encode(signupForm.getPassword()));
        member.setEmail(signupForm.getEmail());
        member.setEmailVerified(false);
        memberRepository.save(member);

        return member;
    }

    private void sendCheckEmail(Member signupMember) {

        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setTo(signupMember.getEmail());
        mailMessage.setSubject("Thanks for signing up");

        mailMessage.setText("http://localhost:8081/check-email-token?token="+ signupMember.getEmailCheckToken()+"&email="+ signupMember.getEmail()
        +"\n"+"?????????????????? ?????????????????????.");
        mailSender.send(mailMessage);
    }

    public Member signup(SignupForm signupForm){
        Member signupMember= saveMember(signupForm);
        signupMember.genToken();
        sendCheckEmail(signupMember);
        return signupMember;
    }

    public void login(Member member){
    UsernamePasswordAuthenticationToken token= new UsernamePasswordAuthenticationToken(
            member.getLoginId(),
            member.getPassword(),
            List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(token);
    }



    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Member member= memberRepository.findMemberByLoginId(loginId).stream().findAny().orElse(null);
        if(member==null){
            throw new UsernameNotFoundException(loginId);
        }
        if(!member.getEmailVerified()){
            throw new UsernameNotFoundException(loginId);
        }


        return new UserAccount(member);
    }


    public Member findMemberAccount(ForgotPasswordForm forgotPasswordForm){
        String id=forgotPasswordForm.getLoginId();
        Member member = memberRepository.findMemberByLoginId(id).stream().findAny().orElse(null);
        if (member==null){
            return null;
        }
        member.genToken();
        sendCheckEmail(member);
        return member;
    }

    public Comment createComment(CommentForm commentForm, Member member, Stock stock)
    {
        Comment comment=new Comment();
        comment.setComment(commentForm.getComment());
        comment.setMember(member);
        comment.setStock(stock);
        comment.setCommentedAt(LocalDateTime.now());
        return  commentRepository.save(comment);

    }


}
