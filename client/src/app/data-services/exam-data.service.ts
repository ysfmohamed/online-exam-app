import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import { Exam } from '../models/Exam.model';

@Injectable({ providedIn: 'root' })
export class ExamDataService {
  constructor(private http: HttpClient) {}

  createExam(exam: Exam) {
    return this.http.post('http://localhost:8083/exam/create', exam);
  }

  fetchExamDefinitions(pageNumber: number, pageSize: number) {
    return this.http.get('http://localhost:8083/exam/exam-definitions', {
      params: new HttpParams()
        .set('pageNumber', pageNumber)
        .set('pageSize', pageSize),
    });
  }

  fetchExamDefinitionsAmount() {
    return this.http.get('http://localhost:8083/exam/exam-definitions-amount');
  }

  fetchExamInstance(id: number) {
    return this.http.get(`http://localhost:8083/exam/instance/${id}`);
  }

  fetchExamDefinition(id: number) {
    return this.http.get(`http://localhost:8083/exam/definition/${id}`);
  }

  startExam(examInstanceId: number, body) {
    return this.http.patch(
      `http://localhost:8083/exam/take/${examInstanceId}`,
      body
    );
  }

  next(examInstanceId: number, answer) {
    return this.http.patch(
      `http://localhost:8083/exam/next/${examInstanceId}`,
      answer
    );
  }

  assignExamToStudent(examId: number, assignExamObj) {
    return this.http
      .post(
        `http://localhost:8083/exam/assign-exam-to-student/${examId}`,
        assignExamObj
      )
      .subscribe((assignedExam) => {
        console.log(assignedExam);
      });
  }
}
