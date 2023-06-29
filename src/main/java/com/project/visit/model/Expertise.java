package com.project.visit.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
public class Expertise implements Serializable {

    @Id
    private long id;

    private String name;

}
