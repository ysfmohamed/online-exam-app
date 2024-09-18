import { Component, Input, OnInit } from '@angular/core';
import { QuestionData } from 'src/app/models/QuestionData';
import { QuestionService } from 'src/app/services/question.service';

@Component({
  selector: 'app-question-item',
  templateUrl: './question-item.component.html',
  styleUrls: ['question-item.component.css'],
})
export class QuestionItemComponent implements OnInit {
  @Input() question: QuestionData;
  @Input() page;
  @Input() itemsPerPage;
  @Input() totalPages;
  @Input() index;
  createExamMode: boolean = false;

  constructor(private questionService: QuestionService) {}

  ngOnInit() {
    this.createExamMode = this.questionService.createExamMode;
  }

  onAddQuestionToExam(questionToBeAddedToExam: string) {
    this.questionService.examQuestions.push(questionToBeAddedToExam);

    console.log(this.questionService.examQuestions);
  }

  onRemoveQuestionFromExam(questionToBeRemovedFromExam: string) {
    const questionIndex = this.questionService.examQuestions.indexOf(
      questionToBeRemovedFromExam
    );

    this.questionService.examQuestions.splice(questionIndex, 1);

    console.log(this.questionService.examQuestions);
  }

  isQuestionAddedToExam(questionId: string) {
    return this.questionService.examQuestions.findIndex(
      (id) => id === questionId
    );
  }
}
