package com.project.visit.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Comment {

    @Id
    private long id;

    private String comment;

    private String userId;

    private String doctorId;
}
