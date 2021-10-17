package com.brightics.prj.web.service;

import com.brightics.prj.web.entity.*;
import com.brightics.prj.web.form.ForgotPasswordForm;
import com.brightics.prj.web.form.MyPageDto;
import com.brightics.prj.web.form.UserAccount;
import com.brightics.prj.web.form.SignupForm;
import com.brightics.prj.web.repository.CommentRepository;
import com.brightics.prj.web.repository.MemberCandidateRepository;
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

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements UserDetailsService {
    private final NewsRepository newsRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;
    private final MemberCandidateRepository memberCandidateRepository;



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
        +"\n"+"가입해주셔서 감사합니다람쥐.");
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

    public MyPageDto myPageInfo(Member member){

        LocalDate yesterday=LocalDate.now().minusDays(1);
        MyPageDto myPageDto= new MyPageDto();
        List<MemberCandidate> memberCandidates = memberCandidateRepository.findAllByMemberIs(member);
        for (MemberCandidate memberCandidate : memberCandidates) {
            Candidate candidate= memberCandidate.getCandidate();
            List<Object[]> count = newsRepository.findCountNumberOfNewsPerPeriodAndCandidateIs(yesterday, 1L, candidate.getId());
            BigInteger beforeYesterdayCount= (BigInteger) count.get(0)[1];
            BigInteger yesterdayCount= (BigInteger) count.get(1)[1];
            Long countNewsPercent;

            if (beforeYesterdayCount.longValue()==0L){
            countNewsPercent=yesterdayCount.longValue()/beforeYesterdayCount.longValue()*100;}
            else{
                countNewsPercent =0L;;
            }
            myPageDto.setCandidate(candidate);
            myPageDto.setCountNewsPercent(countNewsPercent);
        }


        return myPageDto;
    }


}
