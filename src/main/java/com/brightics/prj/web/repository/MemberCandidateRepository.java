package com.brightics.prj.web.repository;


import com.brightics.prj.web.entity.Member;
import com.brightics.prj.web.entity.MemberCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberCandidateRepository extends JpaRepository<MemberCandidate, Long> {

    List<MemberCandidate> findAllByMemberIs(Member member);
}
