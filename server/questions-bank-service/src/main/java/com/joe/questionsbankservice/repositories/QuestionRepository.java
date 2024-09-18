package com.joe.questionsbankservice.repositories;

import com.joe.questionsbankservice.entities.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends MongoRepository<Question, String> { }
