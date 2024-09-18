package com.joe.questionsbankservice.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDto {
    @NotBlank(message = "Answer ID should not be empty")
    private String id;

    @NotBlank(message = "Answer content should not be empty")
    private String content;
}
