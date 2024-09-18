package com.joe.questionsbankservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class QuestionsIdInconsistency extends RuntimeException {
    private String message;

    public QuestionsIdInconsistency(String message) {
        super(message);
    }
}
