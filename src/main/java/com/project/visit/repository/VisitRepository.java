package com.project.visit.repository;

import com.project.visit.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    List<Visit> findAllByUserUserId(String userId);

    Optional<Visit> findByIdAndDoctorUserId(Long id, String userId);

    Optional<Visit> findByIdAndUserUserId(Long id, String userId);

    List<Visit> findAllByDoctorUserIdAndAddressIdAndTimeBetween(String userId, Long addressId, Long from, Long to);

    List<Visit> findAllByAddressIdAndTimeBetween(Long addressId, Long from, Long to);
}
