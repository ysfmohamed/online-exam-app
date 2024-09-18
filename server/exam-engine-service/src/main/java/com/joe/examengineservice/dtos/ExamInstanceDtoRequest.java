package com.joe.examengineservice.dtos;

import com.joe.examengineservice.entities.FutureExam;
import lombok.Data;

@Data
public class ExamInstanceDtoRequest {
    private FutureExam futureExam;
    private Long duration;
    private String createdBy;
    private String takenBy;
}
