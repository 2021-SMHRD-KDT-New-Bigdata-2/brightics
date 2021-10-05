package com.brightics.prj.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Candidate {

    @Id
    @Column(name="candidate_id")
    private int id;
    private String name;
    private String description;

}
