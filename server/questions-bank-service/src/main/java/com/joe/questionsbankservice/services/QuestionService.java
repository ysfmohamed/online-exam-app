package com.joe.questionsbankservice.services;

import com.joe.questionsbankservice.dtos.AnswerDto;
import com.joe.questionsbankservice.dtos.QuestionDto;
import com.joe.questionsbankservice.dtos.QuestionUpdateDto;
import com.joe.questionsbankservice.entities.Answer;
import com.joe.questionsbankservice.entities.Question;
import com.joe.questionsbankservice.exceptions.AnswerNotFoundException;
import com.joe.questionsbankservice.exceptions.QuestionNotFoundException;
import com.joe.questionsbankservice.exceptions.QuestionsIdInconsistency;
import com.joe.questionsbankservice.mappers.AnswerMapper;
import com.joe.questionsbankservice.mappers.QuestionMapper;
import com.joe.questionsbankservice.repositories.QuestionRepository;
import com.joe.questionsbankservice.wrappers.AnswersList;
import com.joe.questionsbankservice.wrappers.QuestionsList;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public long fetchQuestionsAmount() {
        return questionRepository.count();
    }
    public Question createQuestion(QuestionDto questionToBeCreated) {
        Question mappedQuestion = QuestionMapper.mapToQuestionDtoToQuestion(questionToBeCreated);

        return questionRepository.save(mappedQuestion);
    }

    public QuestionsList fetchQuestions(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        List<Question> questions = questionRepository.findAll(pageable).getContent();

        QuestionsList questionsWrapper = new QuestionsList(questions);

        return questionsWrapper;
    }

    public Question findQuestionById(String id) {
        Optional<Question> question = questionRepository.findById(id);

        if(question.isEmpty()) {
            throw new QuestionNotFoundException("Given question id doesn't exist");
        }

        return question.get();
    }


    public Question updateQuestion(String id, QuestionUpdateDto questionToBeUpdated) {
        if (questionToBeUpdated.getId() == null) {
            throw new QuestionNotFoundException("Please provide the question id in the response body.");
        }

        if(questionToBeUpdated.getId().equals(id) == false) {
            throw new QuestionsIdInconsistency("Question id in the path variable is not equal to the question id in the request body." +
                    "Please provide the same question id in the path variable and request body");
        }

        Question question =  findQuestionById(id);

        Question mappedQuestion = QuestionMapper.mapQuestionUpdateToQuestion(questionToBeUpdated, question);

        return questionRepository.save(mappedQuestion);
    }

    public void deleteQuestion(String id) {
        findQuestionById(id);

        questionRepository.deleteById(id);
    }

    public void addAnswerToQuestion(String id, AnswerDto answerToBeAdded) {
        Question question = findQuestionById(id);

        Answer mappedAnswer = AnswerMapper.mapAnswerDtoToAnswer(answerToBeAdded);

        question.getAnswers().add(mappedAnswer);

        questionRepository.save(question);
    }

    public AnswersList deleteAnswerFromQuestion(String questionId, String answerId) {
        Question question = findQuestionById(questionId);

        // assume the answer to be deleted is in the correct answers ids
        boolean isCorrectAnswerRemoved = question.getCorrectAnswersIds().removeIf(correctAnswerId -> correctAnswerId.equals(answerId));

        boolean isAnswerRemoved = question.getAnswers().removeIf(answer -> answer.getId().equals(answerId));

        if(!isAnswerRemoved) {
            throw new AnswerNotFoundException("Cannot delete an answer that doesn't exist");
        }

        Question savedQuestion = questionRepository.save(question);

        return new AnswersList(savedQuestion.getAnswers());
    }
}
