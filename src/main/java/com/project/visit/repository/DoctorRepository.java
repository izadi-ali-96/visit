package com.project.visit.repository;

import com.project.visit.model.Doctor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {


    @EntityGraph("doctor.addresses")
    Optional<Doctor> findByMedicalCode(String code);


    List<Doctor> findAllByAddresses_City_IdAndExpertiseIdInAndActiveIsTrue(Long cityId, List<Long> exp);

    List<Doctor> findAllByAddresses_City_IdAndActiveIsTrue(Long cityId);

    default List<Doctor> findDoctorsByCityId(Long cityId, List<Long> ex) {
        if (ex.isEmpty()) {
            return findAllByAddresses_City_IdAndActiveIsTrue(cityId);
        }
        return findAllByAddresses_City_IdAndExpertiseIdInAndActiveIsTrue(cityId, ex);
    }

    Optional<Doctor> findByUserId(String userId);

}
