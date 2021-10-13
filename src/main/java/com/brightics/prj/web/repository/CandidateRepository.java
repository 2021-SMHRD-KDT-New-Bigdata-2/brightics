package com.brightics.prj.web.repository;

import com.brightics.prj.web.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate,Long> {

}
