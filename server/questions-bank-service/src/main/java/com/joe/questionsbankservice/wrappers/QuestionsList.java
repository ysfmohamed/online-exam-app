package com.joe.questionsbankservice.wrappers;

import com.joe.questionsbankservice.dtos.QuestionDto;
import com.joe.questionsbankservice.entities.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionsList {
    private List<Question> questions;
}
