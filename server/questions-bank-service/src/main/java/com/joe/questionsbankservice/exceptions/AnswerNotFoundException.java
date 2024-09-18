package com.joe.questionsbankservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class AnswerNotFoundException extends RuntimeException {
    private String message;

    public AnswerNotFoundException(String message) {
        super(message);
    }
}
