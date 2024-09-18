package com.joe.questionsbankservice.entities;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    @Id
    @NotBlank(message = "Answer ID should not be empty")
    private String id;

    @NotBlank(message = "Answer content should not be empty")
    private String content;
}
