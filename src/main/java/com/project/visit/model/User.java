package com.project.visit.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User implements Serializable {

	@Id
	private long id;

	private String userId;

	private String name;

	private String family;

	private String phone;

	@OneToMany(targetEntity = Visit.class, mappedBy = "user", fetch = FetchType.LAZY)
	private List<Visit> visits;
}
