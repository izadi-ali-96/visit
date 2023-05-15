package com.project.visit.repository;

import java.util.List;

import com.project.visit.model.Province;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {


	List<Province> findAllById(Long id);

}
