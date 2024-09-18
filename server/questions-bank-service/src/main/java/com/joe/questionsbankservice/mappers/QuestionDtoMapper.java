package com.joe.questionsbankservice.mappers;

import com.joe.questionsbankservice.dtos.QuestionDto;
import com.joe.questionsbankservice.entities.Question;

public class QuestionDtoMapper {
    static public QuestionDto mapToQuestionDto(Question question) {
        return new QuestionDto(
                question.getId(),
                question.getContent(),
                question.getLevelId(),
                question.getTypeId(),
                question.getCategory(),
                question.getSubCategory(),
                question.getMark(),
                question.getExpectedTime(),
                question.getCorrectAnswersIds(),
                question.getCreatedBy(),
                question.getCreatedAt(),
                question.getAnswers()
        );
    }
}
