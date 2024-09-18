package com.joe.examengineservice.repositories;

import com.joe.examengineservice.entities.ExamDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamDefinitionRepository extends JpaRepository<ExamDefinition, Long> { }
