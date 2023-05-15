package com.project.visit.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Province implements Serializable {

	@Id
	private long id;

	private String code;

	private String name;
}
