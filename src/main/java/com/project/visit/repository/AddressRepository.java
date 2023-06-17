package com.project.visit.repository;

import com.project.visit.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByIdAndDoctorUserId(Long id, String userId);
}
