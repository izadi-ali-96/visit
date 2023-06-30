package com.project.visit.model;

import com.project.visit.model.converter.StringListConverter;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    private City city;

    private String path;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Doctor doctor;

    @OneToMany(targetEntity = Visit.class, mappedBy = "address", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Visit> visits = new ArrayList<>();
    @Convert(converter = StringListConverter.class)
    private List<String> days;

    @Convert(converter = StringListConverter.class)
    private List<String> phones;

}
