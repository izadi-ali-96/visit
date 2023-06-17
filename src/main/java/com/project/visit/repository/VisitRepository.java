package com.project.visit.repository;

import com.project.visit.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    List<Visit> findAllByUserUserId(String userId);

    List<Visit> findAllByDoctorUserIdAndAddressIdAndTimeBetween(String userId, Long addressId, Long from, Long to);
}
