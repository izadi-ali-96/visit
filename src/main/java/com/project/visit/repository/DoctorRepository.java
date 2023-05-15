package com.project.visit.repository;

import java.util.List;
import java.util.Optional;

import com.project.visit.model.Doctor;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {


	@EntityGraph("doctor.addresses")
	Optional<Doctor> findByMedicalCode(String code);

	List<Doctor> findAllByAddresses_City_Id(Long cityId);

	default List<Doctor> findDoctorsByCityId(Long cityId) {
		return findAllByAddresses_City_Id(cityId);
	}

}
