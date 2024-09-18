package com.joe.examengineservice.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ExamDefinitionReqDto {
    @NotEmpty(message="Exam's name should not be empty")
    private String name;

    @NotEmpty(message="Exam's creator should not be empty")
    private String createdBy;

    @NotEmpty(message="Exam's passing score should not be empty")
    private Long passingScore;

    @NotEmpty(message="Exam should have at least one question")
    @NotNull(message="Exam should have at least one question")
    private List<String> questions;
}
