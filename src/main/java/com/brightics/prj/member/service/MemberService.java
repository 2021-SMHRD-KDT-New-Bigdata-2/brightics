package com.brightics.prj.member.service;

import com.brightics.prj.web.entity.Stock;
import com.brightics.prj.member.LoginForm;
import com.brightics.prj.member.SignupForm;
import com.brightics.prj.member.entity.Comment;
import com.brightics.prj.member.entity.Member;
import com.brightics.prj.member.entity.MemberRole;
import com.brightics.prj.member.repository.CommentRepository;
import com.brightics.prj.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    public Member register(SignupForm signupForm){
        if(memberRepository.existsMemberByLoginId(signupForm.getLoginId())){
            return null;
        }

        Member member = new Member();
        member.setLoginId(signupForm.getLoginId());

        String hashedPassword = BCrypt.hashpw(signupForm.getPassword(),BCrypt.gensalt());
        member.setPassword(hashedPassword);

        member.setEmail(signupForm.getEmail());
        member.setMemberRole(MemberRole.ROLE_USER);

        memberRepository.save(member);


        return member;
    }

    public Member login(LoginForm loginForm){
       return memberRepository.findMemberByLoginId(loginForm.getLoginId()).stream().filter(
               member -> BCrypt.checkpw(loginForm.getPassword(), member.getPassword())).findAny().orElse(null);

    }

    public Comment CreateComment(String commentText , Member member, Stock stock){

        Comment comment= new Comment();
        comment.setComment(commentText);
        comment.setMember(member);
        comment.setStock(stock);


        commentRepository.save(comment);
        return comment;

    }

    public void deleteComment(Comment comment){


    }
}
