package com.brightics.prj.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Candidate {

    @Id
    @Column(name="candidate_id")
    private Long id;
    private String name;
    private String description;

}
