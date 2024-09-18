package com.joe.examengineservice.controllers;

import com.joe.examengineservice.dtos.ExamDefinitionReqDto;
import com.joe.examengineservice.dtos.ExamInstanceDtoRequest;
import com.joe.examengineservice.entities.ExamDefinition;
import com.joe.examengineservice.entities.ExamInstance;
import com.joe.examengineservice.services.ExamDefinitionService;
import com.joe.examengineservice.services.ExamInstanceService;
import com.joe.examengineservice.wrappers.ExamDefinitionsWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/exam")
public class ExamController {
    private final ExamDefinitionService examDefService;
    private final ExamInstanceService examInsService;

    public ExamController(ExamDefinitionService examDefService, ExamInstanceService examInsService) {
        this.examDefService = examDefService;
        this.examInsService = examInsService;
    }

    @GetMapping("/${endpoint.exam-controller.fetch-exam-definitions}")
    public ResponseEntity<ExamDefinitionsWrapper> fetchExamDefinitions(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return ResponseEntity.status(HttpStatus.OK).body(examDefService.fetchExamDefinitions(pageNumber, pageSize));
    }

    @GetMapping("/${endpoint.exam-controller.fetch-exam-definition}")
    public ResponseEntity<Map<String, Object>> fetchExamDefinition(@PathVariable Long id) {
        ExamDefinition examDefinition = examDefService.fetchExamDefinition(id);

        Map<String, Object> map = new HashMap<>();
        map.put("name", examDefinition.getName());
        map.put("questions", examDefinition.getQuestions());

        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    @GetMapping("/${endpoint.exam-controller.fetch-exam-instance}")
    public ResponseEntity<Map<String, Object>> fetchExamInstance(@PathVariable Long id) {
        ExamInstance examInstance = examInsService.fetchExamInstance(id);

        Map<String, Object> map = new HashMap<>();
        map.put("completionTime", examInstance.getCompletionTime());
        map.put("endTime", examInstance.getEndTime());
        map.put("questions", examInstance.getQuestions());
        map.put("examDefinitionId", examInstance.getExam().getId());
        map.put("status", examInstance.getStatus());
        map.put("futureExam", examInstance.getFutureExam());
        map.put("duration", examInstance.getDuration());

        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    @GetMapping("/${endpoint.exam-controller.fetch-exam-definitions-amount}")
    public ResponseEntity<Map<String, Long>> fetchExamDefinitionsAmount() {
        Long examDefinitionsAmount = examDefService.fetchExamDefinitionsAmount();

        Map<String, Long> map = new HashMap<>();
        map.put("examDefinitionsAmount", examDefinitionsAmount);

        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    @PostMapping("/${endpoint.exam-controller.create-exam}")
    public ResponseEntity<Map<String, Object>> createExam(@RequestBody ExamDefinitionReqDto examToBeCreated) {
        Long examId = examDefService.addExam(examToBeCreated);

        Map<String, Object> map = new HashMap<>();
        map.put("examId", examId);

        return ResponseEntity.status(HttpStatus.CREATED).body(map);
    }

    // http:localhost:4200/exam=1
    @PostMapping("/${endpoint.exam-controller.assign-exam-to-student}")
    public ResponseEntity<Map<String, Object>> assignExamToStudent(@PathVariable Long examId, @RequestBody ExamInstanceDtoRequest examInstanceToBeCreated) {
        ExamInstance createdExamInstance = examInsService.addExamInstance(examId, examInstanceToBeCreated);

        Map<String, Object> map = new HashMap<>();
        map.put("examInstanceId", createdExamInstance.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(map);
    }

    @PatchMapping("/${endpoint.exam-controller.take-exam}")
    public void takeExam(@PathVariable Long examInstanceId, @RequestBody Map<String, Object> reqBody) throws ParseException {
        examInsService.takeExam(examInstanceId, reqBody);
    }

    @PatchMapping("/${endpoint.exam-controller.next}")
    @ResponseStatus(HttpStatus.CREATED)
    public void next(@PathVariable Long examInstanceId, @RequestBody Map<String, Object> reqBody) {
        examInsService.next(examInstanceId, reqBody);
    }
}
