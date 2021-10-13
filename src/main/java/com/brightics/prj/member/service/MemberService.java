package com.brightics.prj.member.service;

import com.brightics.prj.member.MailSender;
import com.brightics.prj.web.entity.Stock;
import com.brightics.prj.member.SignupForm;
import com.brightics.prj.member.entity.Comment;
import com.brightics.prj.member.entity.Member;
import com.brightics.prj.member.entity.MemberRole;
import com.brightics.prj.member.repository.CommentRepository;
import com.brightics.prj.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final MailSender mailSender;



    private Member saveMember(SignupForm signupForm){

        Member member = new Member();
        member.setLoginId(signupForm.getLoginId());

        String hashedPassword = BCrypt.hashpw(signupForm.getPassword(),BCrypt.gensalt());
        member.setPassword(hashedPassword);

        member.setEmail(signupForm.getEmail());
        member.setEmailVerified(false);
        member.setMemberRole(MemberRole.ROLE_USER);

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
        mailMessage.setText("/check-email-token?token="+ signupMember.getEmailCheckToken()+"&email="+ signupMember.getEmail());
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

}
