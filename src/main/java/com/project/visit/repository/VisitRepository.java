package com.project.visit.repository;

import java.util.List;

import com.project.visit.model.Visit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

	List<Visit> findAllByUserUserId(String userId);
}
