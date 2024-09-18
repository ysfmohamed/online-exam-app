package com.joe.examengineservice.repositories;

import com.joe.examengineservice.entities.ExamInstance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamInstanceRepository extends JpaRepository<ExamInstance, Long> { }
