package com.joe.examengineservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    private String id;
    private List<Integer> selectedAnswerId;
    private Long displayTime;
    private Long answerTime;
}
