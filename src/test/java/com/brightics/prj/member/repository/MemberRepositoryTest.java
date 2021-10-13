package com.brightics.prj.member.repository;

import com.brightics.prj.member.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;


    @Test
    void findIdTest(){
        Member member= memberRepository.findMemberByLoginId("test123").get();
        System.out.println(member.getEmail());

    }

}