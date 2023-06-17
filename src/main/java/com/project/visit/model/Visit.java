package com.project.visit.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
public class Visit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Long time;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Address address;
}
