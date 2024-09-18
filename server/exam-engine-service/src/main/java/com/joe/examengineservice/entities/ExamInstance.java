package com.joe.examengineservice.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity(name="exams-instances")
public class ExamInstance {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="exam_id", nullable=false)
    private ExamDefinition exam;

    private Timestamp startedTime; // equals to the time which user presses a button that triggered the exam
    private Timestamp endTime; // when did the student finish the exam
    private Long duration; // exam duration time
    private Timestamp completionTime; // start time + end time
    @JdbcTypeCode(SqlTypes.JSON)
    private FutureExam futureExam;
    private String createdBy;
    private String takenBy;
    private String status;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<Question> questions;

    public ExamInstance(ExamDefinition exam, Long duration, FutureExam futureExam, String createdBy, String takenBy) {
        this.exam = exam;
        this.duration = duration;
        this.futureExam = futureExam;
        this.createdBy = createdBy;
        this.takenBy = takenBy;
        this.questions = new ArrayList<>();
    }
}
