package com.joe.notificationsmanagementservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FutureExam {
    private Long examDefinitionId;
    private Long examInstanceId;
    private String createdBy;
    private Timestamp scheduledTimeFrom;
    private Timestamp scheduledTimeTo;
    private String url;

    public Long getExamDefinitionId() {
        return examDefinitionId;
    }

    public void setExamDefinitionId(Long examDefinitionId) {
        this.examDefinitionId = examDefinitionId;
    }

    public Long getExamInstanceId() {
        return examInstanceId;
    }

    public void setExamInstanceId(Long examInstanceId) {
        this.examInstanceId = examInstanceId;
    }

    public Timestamp getScheduledTimeFrom() {
        return scheduledTimeFrom;
    }

    public void setScheduledTimeFrom(Timestamp scheduledTimeFrom) {
        this.scheduledTimeFrom = scheduledTimeFrom;
    }

    public Timestamp getScheduledTimeTo() {
        return scheduledTimeTo;
    }

    public void setScheduledTimeTo(Timestamp scheduledTimeTo) {
        this.scheduledTimeTo = scheduledTimeTo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
