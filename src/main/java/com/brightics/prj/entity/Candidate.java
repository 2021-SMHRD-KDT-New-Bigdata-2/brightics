package com.brightics.prj.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Candidate {

    @Id
    @GeneratedValue
    @Column(name="candidate_id")
    private Long id;

    private String name;

}
