package com.project.visit.repository;

import com.project.visit.model.Doctor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {


    @EntityGraph("doctor.addresses")
    Optional<Doctor> findByMedicalCode(String code);


    List<Doctor> findAllByAddresses_City_IdAndExpertiseIdInAndActiveIsTrue(Long cityId, List<Long> exp, Sort pageable);

    List<Doctor> findAllByAddresses_City_IdAndActiveIsTrue(Long cityId, Sort pageable);

    default List<Doctor> findDoctorsByCityId(Long cityId, List<Long> ex) {
        if (ex.isEmpty()) {
            return findAllByAddresses_City_IdAndActiveIsTrue(cityId, Sort.by(Sort.Direction.ASC, "id"));
        }
        return findAllByAddresses_City_IdAndExpertiseIdInAndActiveIsTrue(cityId, ex, Sort.by(Sort.Direction.ASC, "id"));
    }

    Optional<Doctor> findByUserId(String userId);

    Optional<Doctor> findByDoctorId(String doctorId);

}
