import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';

import { QuestionDataService } from 'src/app/data-services/question-data.service';
import { QuestionData } from 'src/app/models/QuestionData';
import { QuestionService } from 'src/app/services/question.service';

@Component({
  selector: 'app-question-list',
  templateUrl: './question-list.component.html',
  styleUrls: ['./question-list.component.css'],
})
export class QuestionListComponent implements OnInit, OnDestroy {
  questions: QuestionData[] = [];
  itemsPerPage: number = 6;
  page: number = 1;
  totalPages: number = 0;

  private subscriptionQuestionsService: Subscription;

  constructor(
    private questionDataService: QuestionDataService,
    private questionService: QuestionService
  ) {}

  ngOnInit() {
    this.fetchQuestions(this.page);
    this.fetchQuestionAmount();
  }

  fetchQuestions(page: number) {
    this.questionDataService
      .fetchQuestions(page, this.itemsPerPage)
      .subscribe((questionsData) => {
        this.questions = questionsData['questions'];
        this.questionService.setQuestions(questionsData['questions']);
      });

    this.subscriptionQuestionsService =
      this.questionService.questionsChanged.subscribe(
        (changedQuestions: QuestionData[]) => {
          this.questions = changedQuestions;
        }
      );
  }

  fetchQuestionAmount() {
    this.questionDataService
      .fetchQuestionsAmount()
      .subscribe((questionsAmount: number) => {
        this.totalPages = questionsAmount['questionsAmount'];
      });
  }

  onPageChange(event) {
    this.page = event;
    this.fetchQuestions(event);
  }

  ngOnDestroy() {
    this.subscriptionQuestionsService.unsubscribe();
  }
}
