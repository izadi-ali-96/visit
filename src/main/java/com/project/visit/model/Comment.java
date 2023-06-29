package com.project.visit.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(columnDefinition = "LONGTEXT")
    private String comment;

    private String userId;

    private String doctorId;

    private Long creationDate;
}
