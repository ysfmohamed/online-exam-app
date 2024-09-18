package com.joe.examengineservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@Data
@Entity(name="exams-definitions")
public class ExamDefinition {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String createdBy;
    private Long passingScore;
    private List<String> questions;

    public ExamDefinition(String name, String createdBy, Long passingScore, List<String> questions) {
        this.name = name;
        this.createdBy = createdBy;
        this.passingScore = passingScore;
        this.questions = questions;
    }
}
