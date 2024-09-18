import { Component, OnInit } from '@angular/core';
import { ExamDataService } from '../data-services/exam-data.service';

@Component({
  selector: 'app-exams',
  templateUrl: './exams.component.html',
})
export class ExamsComponent implements OnInit {
  assignExamMode: boolean = false;
  examDefinitions;
  examDefinition;
  itemsPerPage: number = 6;
  page: number = 1;
  totalPages: number = 0;

  constructor(private examDataService: ExamDataService) {}

  ngOnInit() {
    this.fetchExamDefinitions(this.page, this.itemsPerPage);
    this.fetchExamDefinitionsAmount();
  }

  fetchExamDefinitions(pageNumber: number, pageSize: number) {
    this.examDataService
      .fetchExamDefinitions(pageNumber, pageSize)
      .subscribe((exams) => {
        this.examDefinitions = exams['examDefinitions'];
      });
  }

  fetchExamDefinitionsAmount() {
    this.examDataService.fetchExamDefinitionsAmount().subscribe((amount) => {
      console.log(amount['examDefinitionsAmount']);
      this.totalPages = amount['examDefinitionsAmount'];
    });
  }

  onPageChange(event) {
    this.page = event;
    this.fetchExamDefinitions(this.page, this.itemsPerPage);
  }

  onAssignExam(exam) {
    this.examDefinition = exam;
    this.assignExamMode = true;
  }

  onClose() {
    this.assignExamMode = false;
  }
}
