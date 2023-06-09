package com.project.visit.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "doctor")
@Getter
@Setter
@NamedEntityGraph(name = "doctor.addresses",
        attributeNodes = @NamedAttributeNode("addresses")
)
public class Doctor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String userId;

    private String name;

    private String family;

    @Column(unique = true)
    private String medicalCode;

    private boolean active = false;
    @OneToMany(targetEntity = Address.class, mappedBy = "doctor", fetch = FetchType.LAZY)
    private Set<Address> addresses = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Expertise> expertise = new HashSet<>();

    @OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER)
    private Set<Visit> visits = new HashSet<>();

    public String getFullName() {
        return name + " " + family;
    }
}
