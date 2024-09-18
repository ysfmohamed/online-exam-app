package com.joe.questionsbankservice.wrappers;

import com.joe.questionsbankservice.dtos.AnswerDto;
import com.joe.questionsbankservice.entities.Answer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswersList {
    List<Answer> answers;
}
