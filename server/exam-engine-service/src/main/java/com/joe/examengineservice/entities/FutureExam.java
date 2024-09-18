package com.joe.examengineservice.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_TIMEZONE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class FutureExam {
    private Timestamp scheduledTimeFrom;

    private Timestamp scheduledTimeTo;

    private String url;
}
