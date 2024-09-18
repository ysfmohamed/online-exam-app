import { Component, OnInit, Input } from '@angular/core';
import { QuestionService } from '../services/question.service';

@Component({
  selector: 'app-questions',
  templateUrl: './questions.component.html',
  styleUrls: ['./questions.component.css'],
})
export class QuestionsComponent implements OnInit {
  @Input() createExamMode: boolean = false;

  constructor(private questionsService: QuestionService) {}

  ngOnInit() {
    if (this.createExamMode) {
      this.questionsService.createExamMode = true;
    } else {
      this.questionsService.createExamMode = false;
    }
  }
}
