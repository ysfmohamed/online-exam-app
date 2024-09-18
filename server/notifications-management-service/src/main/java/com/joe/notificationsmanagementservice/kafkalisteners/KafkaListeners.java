package com.joe.notificationsmanagementservice.kafkalisteners;

import com.joe.notificationsmanagementservice.models.FutureExam;
import com.joe.notificationsmanagementservice.services.ExamNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaListeners {
    private final ExamNotificationService examNotiService;

    public KafkaListeners(ExamNotificationService examNotiService) {
        this.examNotiService = examNotiService;
    }

    @KafkaListener(topics="exams", groupId="main", containerFactory="factory")
    public void listener(@Header(KafkaHeaders.RECEIVED_KEY) String user, FutureExam examToBeTaken) {
        log.info("Key: " + user);
        log.info("Listener received: " + examToBeTaken);
        examNotiService.addExamNotification(user, examToBeTaken);
    }
}
