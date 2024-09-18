import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';

// Models
import { Question } from '../models/Question.model';
import { QuestionData } from '../models/QuestionData';

@Injectable({ providedIn: 'root' })
export class QuestionDataService {
  constructor(private http: HttpClient) {}

  storeQuestion(question: Question) {
    this.http
      .post('http://localhost:8081/questions/create', question)
      .subscribe(
        (response) => {
          console.log(response);
        },
        (error) => {
          console.log(error);
        }
      );
  }

  fetchQuestions(pageNumber: number, pageSize: number) {
    return this.http.get('http://localhost:8081/questions/', {
      params: new HttpParams()
        .set('pageNumber', pageNumber)
        .set('pageSize', pageSize),
    });
  }

  updateQuestion(questionId: string, questionData: Question) {
    // console.log(questionData);
    return this.http.put(
      `http://localhost:8081/questions/update/${questionId}`,
      questionData
    );
  }

  deleteAnswerFromQuestion(questionId: string, answerId: number) {
    return this.http.delete(
      `http://localhost:8081/questions/delete-answer-from-question/${questionId}/${answerId}`
    );
  }

  addAnswerToQuestion(questionId: string, answerToBeAdded) {
    return this.http.patch(
      `http://localhost:8081/add-answer-to-question/${questionId}`,
      answerToBeAdded
    );
  }

  deleteQuestion(questionId: string) {
    return this.http.delete(
      `http://localhost:8081/questions/delete/${questionId}`
    );
  }

  fetchQuestion(questionId: string) {
    return this.http.get(
      `http://localhost:8081/questions/question/${questionId}`
    );
  }

  fetchQuestionsAmount() {
    return this.http.get('http://localhost:8081/questions/count');
  }
}
