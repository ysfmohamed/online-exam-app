package com.joe.examengineservice.services;

import com.joe.examengineservice.dtos.ExamDefinitionReqDto;
import com.joe.examengineservice.entities.ExamDefinition;
import com.joe.examengineservice.repositories.ExamDefinitionRepository;
import com.joe.examengineservice.wrappers.ExamDefinitionsWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ExamDefinitionService {
    private final ExamDefinitionRepository examDefRepository;

    public ExamDefinitionService(ExamDefinitionRepository examDefRepository) {
        this.examDefRepository = examDefRepository;
    }

    public ExamDefinition fetchExamDefinition(Long id) {
        return examDefRepository.findById(id).get();
    }

    public Long addExam(ExamDefinitionReqDto examToBeCreated) {
        ExamDefinition examDef = new ExamDefinition(
                examToBeCreated.getName(),
                examToBeCreated.getCreatedBy(),
                examToBeCreated.getPassingScore(),
                examToBeCreated.getQuestions());

        ExamDefinition createdExam = examDefRepository.save(examDef);

        return createdExam.getId();
    }

    public ExamDefinitionsWrapper fetchExamDefinitions(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        List<ExamDefinition> examDefinitions = examDefRepository.findAll(pageable).getContent();

        return new ExamDefinitionsWrapper(examDefinitions);
    }

    public long fetchExamDefinitionsAmount() {
        return examDefRepository.findAll().size();
    }

    public ExamDefinition findExamDefinition(Long examId) {
        return examDefRepository.findById(examId).get();
    }
}
