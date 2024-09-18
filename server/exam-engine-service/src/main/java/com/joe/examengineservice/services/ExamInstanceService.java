package com.joe.examengineservice.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.joe.examengineservice.dtos.ExamInstanceDtoRequest;
import com.joe.examengineservice.entities.ExamDefinition;
import com.joe.examengineservice.entities.ExamInstance;
import com.joe.examengineservice.entities.FutureExam;
import com.joe.examengineservice.entities.Question;
import com.joe.examengineservice.repositories.ExamDefinitionRepository;
import com.joe.examengineservice.repositories.ExamInstanceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.print.attribute.standard.Media;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ExamInstanceService {
    private final ExamInstanceRepository examInsRepository;
    private final ExamDefinitionService examDefService;

    public ExamInstanceService(ExamInstanceRepository examInsRepository, ExamDefinitionService examDefService) {
        this.examInsRepository = examInsRepository;
        this.examDefService = examDefService;
    }

    public ExamInstance fetchExamInstance(Long id) {
        return examInsRepository.findById(id).get();
    }

    public ExamInstance addExamInstance(Long examId, ExamInstanceDtoRequest examInstanceToBeCreated) {
        ExamDefinition examDef = examDefService.findExamDefinition(examId);

        log.info("Exam Definition Info: " + examDef.toString());

        FutureExam futureExam = new FutureExam(
                examInstanceToBeCreated.getFutureExam().getScheduledTimeFrom(),
                examInstanceToBeCreated.getFutureExam().getScheduledTimeTo(),
                examInstanceToBeCreated.getFutureExam().getUrl()
                );

        ExamInstance examInstance = new ExamInstance(
                examDef,
                examInstanceToBeCreated.getDuration(),
                futureExam,
                examInstanceToBeCreated.getCreatedBy(),
                examInstanceToBeCreated.getTakenBy()
        );

        log.info("Exam Instance Info: " + examInstance.toString());

        ExamInstance createdExamInstance = examInsRepository.save(examInstance);

        ResponseEntity<String> response = publishExamInstance(createdExamInstance, examId);

        return createdExamInstance;
    }

    private ResponseEntity<String> publishExamInstance(ExamInstance createdExamInstance, Long examId) {
        RestTemplate rest = new RestTemplate();

        HttpHeaders httpHeaders = buildHttpHeaders(createdExamInstance);
        Map<String, Object> map = buildHttpBody(createdExamInstance, examId);

        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(map, httpHeaders);

        log.info("http headers: " + httpEntity.getHeaders().toString());
        log.info("http body: " + httpEntity.getBody().toString());

        ResponseEntity<String> response = rest.postForEntity("http://localhost:8084/message/publish-exam", httpEntity, String.class);

        log.info("Response info: " + response.toString());

        return response;
    }

    private HttpHeaders buildHttpHeaders(ExamInstance createdExamInstance) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("user", createdExamInstance.getTakenBy());

        return httpHeaders;
    }

    private Map<String, Object> buildHttpBody(ExamInstance createdExamInstance, Long examId) {
        Map<String, Object> map = new HashMap<>();
        map.put("examDefinitionId", examId);
        map.put("examInstanceId", createdExamInstance.getId());
        map.put("createdBy", createdExamInstance.getCreatedBy());
        map.put("scheduledTimeFrom", createdExamInstance.getFutureExam().getScheduledTimeFrom());
        map.put("scheduledTimeTo", createdExamInstance.getFutureExam().getScheduledTimeTo());
        map.put("url", createdExamInstance.getFutureExam().getUrl());

        return map;
    }

    public ExamInstance findExamInstance(Long examInstanceId) {
        return examInsRepository.findById(examInstanceId).get();
    }

    public void takeExam(Long examInstanceId, Map<String, Object> body) {
        ExamInstance examInstance = findExamInstance(examInstanceId);

        Long startedTime = (Long) body.get("startedTime");
        Long endTime = (Long) body.get("endTime");

        examInstance.setStartedTime(new Timestamp(startedTime));
        examInstance.setEndTime(new Timestamp(endTime));
        examInstance.setStatus(body.get("status").toString());

        examInsRepository.save(examInstance);
    }

    public void next(Long examId, Map<String, Object> answer) {
        List<Integer> selectedAnswersIds = (List) answer.get("selectedAnswersIds");
        log.info("Selected answers ids: " + selectedAnswersIds.toString());

        Question question = new
                Question((String) answer.get("questionId"),
                        selectedAnswersIds,
                        Long.valueOf((Integer)answer.get("displayTime")),
                        (Long)answer.get("answerTime"));

        ExamInstance examInstance = findExamInstance(examId);

        examInstance.getQuestions().add(question);

        Object completionTime = answer.get("completionTime");
        if(completionTime != null) {
            examInstance.setCompletionTime(new Timestamp((Long)completionTime));
        }

        examInsRepository.save(examInstance);
    }
}
