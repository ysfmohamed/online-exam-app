import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { QuestionService } from 'src/app/services/question.service';

@Component({
  selector: 'app-questions-modal',
  templateUrl: './questions-modal.component.html',
  styleUrls: ['./questions-modal.component.css'],
})
export class QuestionsModalComponent implements OnInit {
  @Output() close = new EventEmitter<boolean>();

  constructor(private questionsService: QuestionService) {}

  ngOnInit() {
    this.questionsService.createExamMode = true;
  }

  onClose() {
    this.close.emit(false);
  }
}
