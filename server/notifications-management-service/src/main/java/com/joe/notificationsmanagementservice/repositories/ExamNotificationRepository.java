package com.joe.notificationsmanagementservice.repositories;

import com.joe.notificationsmanagementservice.entities.ExamNotification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ExamNotificationRepository extends MongoRepository<ExamNotification, String> {
    public List<ExamNotification> findByUser(String user);
}
