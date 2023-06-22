package com.project.visit.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String userId;

    private String name;

    private String family;

    @Column(unique = true)
    private String phone;

    private String password;

    @OneToMany(targetEntity = Visit.class, mappedBy = "user", fetch = FetchType.LAZY)
    private List<Visit> visits;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();

    public String getFullName() {
        return name + " " + family;
    }

}
