package com.joe.questionsbankservice.mappers;

import com.joe.questionsbankservice.dtos.QuestionDto;
import com.joe.questionsbankservice.dtos.QuestionUpdateDto;
import com.joe.questionsbankservice.entities.Question;

public class QuestionMapper {
    static public Question mapToQuestionDtoToQuestion(QuestionDto questionToBeCreated) {
        return new Question(
                questionToBeCreated.getContent(),
                questionToBeCreated.getLevelId(),
                questionToBeCreated.getTypeId(),
                questionToBeCreated.getCategory(),
                questionToBeCreated.getSubCategory(),
                questionToBeCreated.getMark(),
                questionToBeCreated.getExpectedTime(),
                questionToBeCreated.getCorrectAnswersIds(),
                questionToBeCreated.getCreatedBy(),
                questionToBeCreated.getCreatedAt(),
                questionToBeCreated.getAnswers()
        );
    }

    static public Question mapQuestionUpdateToQuestion(QuestionUpdateDto questionToBeUpdated, Question question) {
        return new Question(
                questionToBeUpdated.getId(),
                questionToBeUpdated.getContent(),
                questionToBeUpdated.getLevelId(),
                questionToBeUpdated.getTypeId(),
                questionToBeUpdated.getCategory(),
                questionToBeUpdated.getSubCategory(),
                questionToBeUpdated.getMark(),
                questionToBeUpdated.getExpectedTime(),
                questionToBeUpdated.getCorrectAnswersIds(),
                question.getCreatedBy(),
                question.getCreatedAt(),
                questionToBeUpdated.getAnswers()
        );
    }
}
