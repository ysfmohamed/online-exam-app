package com.joe.questionsbankservice.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, Object> subErrors = buildValidationErrors(ex);

        return buildResponseEntity(
                new ErrorDetails(
                        LocalDateTime.now(),
                        "Input validation errors",
                        request.getDescription(false),
                        HttpStatus.BAD_REQUEST,
                        subErrors)
        );
    }

    @ExceptionHandler(value = QuestionNotFoundException.class)
    public ResponseEntity<Object> handleQuestionNotFoundException(QuestionNotFoundException ex, WebRequest request) {

        return buildResponseEntity(
                new ErrorDetails(
                        LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false),
                        HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(value = QuestionsIdInconsistency.class)
    public ResponseEntity<Object> handleQuestionsIdInconsistency(QuestionsIdInconsistency ex, WebRequest request) {
        return buildResponseEntity(
                new ErrorDetails(
                        LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false),
                        HttpStatus.BAD_REQUEST
                )
        );
    }

    @ExceptionHandler(value = AnswerNotFoundException.class)
    public ResponseEntity<Object> handleAnswerNotFoundException(AnswerNotFoundException ex, WebRequest request) {
        return buildResponseEntity(
                new ErrorDetails(
                        LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false),
                        HttpStatus.NOT_FOUND)
        );
    }

    private ResponseEntity<Object> buildResponseEntity(ErrorDetails errorResponseDetails) {
        return new ResponseEntity<>(errorResponseDetails, errorResponseDetails.getStatus());
    }

    private Map<String, Object> buildValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, Object> subErrors = new HashMap<>();

        for(var s: ex.getFieldErrors()) {
            subErrors.put(s.getField(), s.getDefaultMessage());
        }

        return subErrors;
    }
}
