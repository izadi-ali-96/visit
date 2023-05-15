package com.project.visit.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class City implements Serializable {

	@Id
	private long id;

	private String name;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Province province;
}
