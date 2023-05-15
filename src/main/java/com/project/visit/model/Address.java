package com.project.visit.model;

import java.io.Serializable;
import java.util.List;

import com.project.visit.model.converter.StringListConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Address implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String title;

	@ManyToOne(fetch = FetchType.EAGER)
	private City city;

	private String path;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Doctor doctor;

	@Convert(converter = StringListConverter.class)
	private List<String> days;

	@Convert(converter = StringListConverter.class)
	private List<String> phones;

}
