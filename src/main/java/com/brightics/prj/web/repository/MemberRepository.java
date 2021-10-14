package com.brightics.prj.web.repository;

import com.brightics.prj.web.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findMemberByLoginId(String loginId);
    Member findMemberByEmail(String email);

    Boolean existsMemberByLoginId(String loginId);
    Boolean existsMemberByEmail(String email);
}
