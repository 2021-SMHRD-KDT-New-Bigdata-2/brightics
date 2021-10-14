package com.brightics.prj.member.service;

import com.brightics.prj.member.MailSender;
import com.brightics.prj.member.UserAccount;
import com.brightics.prj.web.entity.Stock;
import com.brightics.prj.member.SignupForm;
import com.brightics.prj.member.entity.Comment;
import com.brightics.prj.member.entity.Member;
import com.brightics.prj.member.repository.CommentRepository;
import com.brightics.prj.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;



    private Member saveMember(SignupForm signupForm){
        Member member = new Member();
        member.setLoginId(signupForm.getLoginId());
        member.setPassword(passwordEncoder.encode(signupForm.getPassword()));
        member.setEmail(signupForm.getEmail());
        member.setEmailVerified(false);
        memberRepository.save(member);

        return member;
    }

    public Comment CreateComment(String commentText , Member member, Stock stock){
        Comment comment= new Comment();
        comment.setComment(commentText);
        comment.setMember(member);
        comment.setStock(stock);
        commentRepository.save(comment);
        return comment;

    }

    private void sendCheckEmail(Member signupMember) {

        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setTo(signupMember.getEmail());
        mailMessage.setSubject("Thanks for signing up");
        mailMessage.setText("localhost:8081/check-email-token?token="+ signupMember.getEmailCheckToken()+"&email="+ signupMember.getEmail());
        mailSender.send(mailMessage);
    }

    public Member signup(SignupForm signupForm){
        Member signupMember= saveMember(signupForm);
        signupMember.genToken();
        sendCheckEmail(signupMember);
        return signupMember;
    }

    public void deleteComment(Comment comment){
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
}
