package com.joe.notificationsmanagementservice.services;

import com.joe.notificationsmanagementservice.entities.ExamNotification;
import com.joe.notificationsmanagementservice.models.FutureExam;
import com.joe.notificationsmanagementservice.repositories.ExamNotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamNotificationService {
    private final ExamNotificationRepository examNotiRepo;

    public ExamNotificationService(ExamNotificationRepository examNotiRepo) {
        this.examNotiRepo = examNotiRepo;
    }

    public void addExamNotification(String user, FutureExam futureExam) {
        ExamNotification examNotification = new ExamNotification(
                user,
                futureExam.getCreatedBy(),
                futureExam.getExamDefinitionId(),
                futureExam.getExamInstanceId(),
                futureExam.getScheduledTimeFrom(),
                futureExam.getScheduledTimeTo(),
                futureExam.getUrl()
                );

        examNotiRepo.save(examNotification);
    }

    public List<ExamNotification> getUserExams(String username) {
        return examNotiRepo.findByUser(username);
    }
}
