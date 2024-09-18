package com.joe.notificationsmanagementservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="exams-notifications")
public class ExamNotification {
    @Id
    private String id;
    private String user;

    private String createdBy;
    private Long examDefinitionId;
    private Long examInstanceId;
    private Timestamp scheduledTimeFrom;
    private Timestamp scheduledTimeTo;
    private String url;

    public ExamNotification(String user, Long examDefinitionId, Long examInstanceId, Timestamp scheduledTimeFrom, Timestamp scheduledTimeTo, String url) {
        this.user = user;
        this.examDefinitionId = examDefinitionId;
        this.examInstanceId = examInstanceId;
        this.scheduledTimeFrom = scheduledTimeFrom;
        this.scheduledTimeTo = scheduledTimeTo;
        this.url = url;
    }

    public ExamNotification(String user, String createdBy, Long examDefinitionId, Long examInstanceId, Timestamp scheduledTimeFrom, Timestamp scheduledTimeTo, String url) {
        this.user = user;
        this.createdBy = createdBy;
        this.examDefinitionId = examDefinitionId;
        this.examInstanceId = examInstanceId;
        this.scheduledTimeFrom = scheduledTimeFrom;
        this.scheduledTimeTo = scheduledTimeTo;
        this.url = url;
    }
}
