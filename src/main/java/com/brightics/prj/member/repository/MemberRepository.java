package com.brightics.prj.member.repository;

import com.brightics.prj.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findMemberByLoginId(String loginId);

    Boolean existsMemberByLoginId(String loginId);
    Boolean existsMemberByEmail(String email);
}
