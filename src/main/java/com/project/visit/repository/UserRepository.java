package com.project.visit.repository;

import java.util.Optional;

import com.project.visit.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUserId(String userId);

	Optional<User> findByPhone(String phone);
}
