package com.joe.examengineservice.wrappers;

import com.joe.examengineservice.entities.ExamDefinition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExamDefinitionsWrapper {
    List<ExamDefinition> examDefinitions;
}
