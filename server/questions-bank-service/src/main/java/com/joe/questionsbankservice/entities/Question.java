package com.joe.questionsbankservice.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_TIMEZONE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "questions")
public class Question {
    @Id
    private String id;

    private String content;

    @Field(name = "level_id")
    private String levelId; // 1 => easy, 2=> medium, 3 => hard, 4 => expert

    @Field(name = "type_id")
    private String typeId; // 1 => mcq, 2 => complete, 3 => T/F, etc ...

    private String category;

    @Field(name = "sub_category")
    private String subCategory;

    @Field(name = "question_mark")
    private BigDecimal mark;

    @Field(name = "expected_time")
    private int expectedTime;

    @Field(name = "correct_answers_ids")
    private List<@NotBlank(message = "Correct answer id should not be empty") String> correctAnswersIds;

    @Field(name = "created_by")
    private String createdBy; // teacher_id string

    @Field(name = "created_at")
    @JsonFormat(timezone =  DEFAULT_TIMEZONE ) // pattern = [ Month/Day/Year Hour:Minute:Second AM/PM ]
    private LocalDateTime createdAt;

    private List<@Valid Answer> answers;

    public Question(String content, String levelId, String typeId, String category, String subCategory, BigDecimal mark, int expectedTime, List<@NotBlank(message = "Correct answer id should not be empty") String> correctAnswersIds, String createdBy, LocalDateTime createdAt, List<@Valid Answer> answers) {
        this.content = content;
        this.levelId = levelId;
        this.typeId = typeId;
        this.category = category;
        this.subCategory = subCategory;
        this.mark = mark;
        this.expectedTime = expectedTime;
        this.correctAnswersIds = correctAnswersIds;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.answers = answers;
    }
}
