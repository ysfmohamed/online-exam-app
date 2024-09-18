package com.joe.questionsbankservice.mappers;

import com.joe.questionsbankservice.dtos.AnswerDto;
import com.joe.questionsbankservice.entities.Answer;

public class AnswerMapper {
    static public Answer mapAnswerDtoToAnswer(AnswerDto answerToBeMapper) {
        return new Answer(answerToBeMapper.getId(), answerToBeMapper.getContent());
    }
}
