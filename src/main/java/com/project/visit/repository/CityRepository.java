package com.project.visit.repository;

import java.util.List;

import com.project.visit.model.City;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

	List<City> findAllByProvinceId(Long id);

}
