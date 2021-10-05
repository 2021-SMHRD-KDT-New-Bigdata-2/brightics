package com.brightics.prj.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@SequenceGenerator(
        name = "candidate_id_generator",
        sequenceName = "candidate_id", // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 1,
        allocationSize = 1)
public class Candidate {

    @Id
    @GeneratedValue
    @Column(name="candidate_id")
    private Long id;

    private String name;

    public Candidate() {

    }
}
