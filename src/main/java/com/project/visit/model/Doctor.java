package com.project.visit.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

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

	@OneToMany(targetEntity = Address.class, mappedBy = "doctor", fetch = FetchType.LAZY)
	private Set<Address> addresses = new HashSet<>();

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Expertise> expertise = new HashSet<>();

	@OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER)
	private Set<Visit> visits = new HashSet<>();

}
