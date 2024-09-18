package com.joe.notificationsmanagementservice.controllers;

import com.joe.notificationsmanagementservice.entities.ExamNotification;
import com.joe.notificationsmanagementservice.models.FutureExam;
import com.joe.notificationsmanagementservice.services.ExamNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/message")
public class NotificationsController {
    private final Logger logger = LoggerFactory.getLogger(NotificationsController.class);
    private final KafkaTemplate<String, FutureExam> kafkaTemplate;
    private final ExamNotificationService examNotiService;

    public NotificationsController(KafkaTemplate<String, FutureExam> kafkaTemplate, ExamNotificationService examNotiService) {
        this.kafkaTemplate = kafkaTemplate;
        this.examNotiService = examNotiService;
    }

    @PostMapping(path="/publish-exam", consumes= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> publishExam(@RequestHeader("user") String user, @RequestBody FutureExam futureExam) {
        logger.info("Message before sent: " + futureExam.toString());
        logger.info("Key: " + user);
        kafkaTemplate.send("exams", user, futureExam);
        logger.info("Message received: " + futureExam);
        return ResponseEntity.ok("Message sent to kafka topic");
    }

    @GetMapping("/user-exams")
    public ResponseEntity<Map<String, List<ExamNotification>>> fetchUserExams(@RequestParam String username) {
        List<ExamNotification> userExams = examNotiService.getUserExams(username);

        Map<String, List<ExamNotification>> map = new HashMap();
        map.put("userExams", userExams);

        return ResponseEntity.status(HttpStatus.OK).body(map);
    }
}
