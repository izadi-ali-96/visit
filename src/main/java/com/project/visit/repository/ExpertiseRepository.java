package com.project.visit.repository;

import com.project.visit.model.Expertise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ExpertiseRepository extends JpaRepository<Expertise, Long> {

    Set<Expertise> findAllByIdIn(List<Long> values);
}
