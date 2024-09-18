package com.joe.questionsbankservice.dtos;

import com.joe.questionsbankservice.entities.Answer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.mongodb.core.mapping.Unwrapped;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
    @Null(message = "Id is generated automatically, you should not send the Id value")
    private String id;

    @NotBlank(message = "Question content should not be empty")
    private String content;

    @NotBlank(message = "Question difficulty should not be empty")
    private String levelId;

    @NotBlank(message = "Question type should not be empty")
    private String typeId;

    @NotBlank(message = "Question category should not be empty")
    private String category;

    private String subCategory;

    @NotNull(message = "Question mark value is mandatory")
    @Range(min = 1L, max = 20L, message = "Question mark value must be between 1.0 and 20.0")
    private BigDecimal mark;

    @NotNull(message = "Expected time value to solve a question should not be empty")
    @Min(value = 1, message="Expected time value to solve a question should not be equal to or less than 0")
    private int expectedTime;

    @NotNull(message = "Question's correct answers are mandatory")
    @NotEmpty(message = "Question's correct answers should not be empty")
    private List<@NotBlank(message = "Correct answer id should not be empty") String> correctAnswersIds;

    @NotBlank(message = "Question creator should not be empty")
    private String createdBy;

    @NotNull(message = "Question creation date is mandatory")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss") // JavaScript: const createdAt = new Date(Date.now()).toLocalString()
    //@Past(message="Question creation data should not be in the past")
    private LocalDateTime createdAt;

    @NotNull(message = "Question's answers are mandatory")
    @NotEmpty(message = "Question's answers should not be empty")
    private List<@Valid Answer> answers;
}
