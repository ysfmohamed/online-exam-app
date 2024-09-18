import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { AuthDataService } from '../data-services/auth-data.service';
import { AuthenticatedUser } from '../models/AuthenticatedUser.model';
import { QuestionService } from '../services/question.service';

import { Exam } from '../models/Exam.model';
import { ExamDataService } from '../data-services/exam-data.service';

@Component({
  selector: 'app-create-exam',
  templateUrl: './create-exam.component.html',
  styleUrls: ['./create-exam.component.css'],
})
export class CreateExamComponent implements OnInit {
  user: AuthenticatedUser = null;
  examForm: FormGroup;
  isLoading: boolean = false;
  chooseQuestionsMode: boolean = false;

  constructor(
    private authDataService: AuthDataService,
    private questionService: QuestionService,
    private examDataService: ExamDataService
  ) {}

  ngOnInit() {
    this.initializeForm();
    this.authDataService.authenticatedUser.subscribe((user) => {
      this.user = user;
    });
  }

  initializeForm() {
    this.examForm = new FormGroup({
      examName: new FormControl(),
      passingScore: new FormControl(),
    });
  }

  createExam() {
    this.isLoading = true;

    const examName = this.examForm.value['examName'];
    const passingScore = this.examForm.value['passingScore'];
    const username = this.user.username;
    const questions = this.questionService.examQuestions;

    const exam = new Exam(examName, username, passingScore, questions);

    this.examDataService.createExam(exam).subscribe((exam) => {
      console.log(exam['examId']);
      this.isLoading = false;
      const examQuestionLen: number = this.questionService.examQuestions.length;

      this.questionService.examQuestions.splice(0, examQuestionLen);
      this.examForm.reset();
    });
  }

  onChooseQuestions() {
    this.chooseQuestionsMode = true;
  }

  onClose() {
    this.chooseQuestionsMode = false;
  }
}
