package com.joe.questionsbankservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joe.questionsbankservice.dtos.AnswerDto;
import com.joe.questionsbankservice.dtos.QuestionDto;
import com.joe.questionsbankservice.dtos.QuestionUpdateDto;
import com.joe.questionsbankservice.entities.Answer;
import com.joe.questionsbankservice.entities.Question;

import com.joe.questionsbankservice.mappers.AnswerMapper;
import com.joe.questionsbankservice.mappers.QuestionDtoMapper;
import com.joe.questionsbankservice.services.QuestionService;
import com.joe.questionsbankservice.wrappers.AnswersList;
import com.joe.questionsbankservice.wrappers.QuestionsList;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@CrossOrigin
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("${endpoint.question-controller.fetch-questions}")
    public ResponseEntity<QuestionsList> fetchQuestions(@RequestParam int pageNumber, @RequestParam int pageSize) {
        QuestionsList questions = questionService.fetchQuestions(pageNumber, pageSize);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(questions);
    }

    @GetMapping("/${endpoint.question-controller.fetch-questions-count}")
    public ResponseEntity<Map<String, Long>> fetchQuestionsAmount() {
        long questionsAmount = questionService.fetchQuestionsAmount();

        Map<String, Long> responseBody = new HashMap<String, Long>();
        responseBody.put("questionsAmount", questionsAmount);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBody);
    }

    @GetMapping("/${endpoint.question-controller.fetch-question}")
    public ResponseEntity<QuestionDto> fetchQuestion(@PathVariable String id) {
        Question question = questionService.findQuestionById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(QuestionDtoMapper.mapToQuestionDto(question));
    }

    @PostMapping("/${endpoint.question-controller.create-question}")
    public ResponseEntity<Object> createQuestion(@Valid @RequestBody QuestionDto questionToBeCreated) {
        Question savedQuestion = questionService.createQuestion(questionToBeCreated);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedQuestion.getId()).toUri();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION, location.toString())
                .build();
    }

    @PutMapping("/${endpoint.question-controller.update-question}")
    public ResponseEntity<Object> updateQuestion(@PathVariable String id,@RequestBody QuestionUpdateDto questionToBeUpdated) {

        Question updatedQuestion = questionService.updateQuestion(id, questionToBeUpdated);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/${endpoint.question-controller.delete-question}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteQuestion(@PathVariable String id) {
        questionService.deleteQuestion(id);
    }

    @PatchMapping("/${endpoint.question-controller.add-answer-to-question}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void addAnswerToQuestion(@PathVariable String id, @Valid @RequestBody AnswerDto answerToBeAdded) {
        questionService.addAnswerToQuestion(id, answerToBeAdded);
    }

    @DeleteMapping("/${endpoint.question-controller.delete-answer-from-question}")
    public ResponseEntity<AnswersList> deleteAnswerFromQuestion(@PathVariable String questionId, @PathVariable String answerId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(questionService.deleteAnswerFromQuestion(questionId, answerId));
    }
}
