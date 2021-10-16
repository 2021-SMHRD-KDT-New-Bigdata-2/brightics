package com.brightics.prj.web.entity;

import javax.persistence.*;

@Entity
@Table(name="member_table")
public class MemberCandidate {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Candidate candidate;
}
