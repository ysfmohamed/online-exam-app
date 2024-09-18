import { Injectable } from '@angular/core';

import { QuestionData } from '../models/QuestionData';
import { Subject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class QuestionService {
  questionsChanged = new Subject<QuestionData[]>();
  private questions: QuestionData[] = [];
  examQuestions: string[] = [];
  createExamMode: boolean = false;

  getQuestions() {
    return this.questions.slice();
  }

  updateQuestion(questionId, updatedQuetion) {
    const questionIndex = this.questions.findIndex(
      (question) => question.id === questionId
    );
    this.questions[questionIndex] = updatedQuetion;
    this.questionsChanged.next(this.questions.slice());
  }

  setQuestions(questions: QuestionData[]) {
    this.questions = questions;
    this.questionsChanged.next(this.questions.slice());
  }
}
