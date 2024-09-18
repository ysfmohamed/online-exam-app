package com.joe.questionsbankservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("com.joe.questionsbankservice.repositories")
@ComponentScan("com.joe.*")
public class QuestionsBankServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuestionsBankServiceApplication.class, args);
	}

}
