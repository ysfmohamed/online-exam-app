import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { ExamDataService } from '../data-services/exam-data.service';
import { AuthDataService } from '../data-services/auth-data.service';
import { AuthenticatedUser } from '../models/AuthenticatedUser.model';

@Component({
  selector: 'app-assign-exam-modal',
  templateUrl: './assign-exam-modal.component.html',
  styleUrls: ['./assign-exam-modal.component.css'],
})
export class AssignExamModalComponent implements OnInit {
  user: AuthenticatedUser;
  assignExamForm: FormGroup;
  @Input() examToBeAssigned;
  @Output() close = new EventEmitter();

  constructor(
    private authDataService: AuthDataService,
    private examDataService: ExamDataService
  ) {
    this.initializeAssignExamForm();
  }

  ngOnInit() {
    this.getAuthenticatedUser();
  }

  initializeAssignExamForm() {
    this.assignExamForm = new FormGroup({
      scheduledTimeFrom: new FormControl(),
      scheduledTimeTo: new FormControl(),
      examDuration: new FormControl(),
      studentUsername: new FormControl(),
    });
  }

  getAuthenticatedUser() {
    this,
      this.authDataService.authenticatedUser.subscribe((authenticatedUser) => {
        this.user = authenticatedUser;
      });
  }

  onSubmit() {
    const assignExamObj: {
      futureExam: {
        scheduledTimeFrom: string;
        scheduledTimeTo: string;
        url: string;
      };
      duration: number;
      takenBy: string;
      createdBy: string;
    } = {
      futureExam: {
        scheduledTimeFrom: this.assignExamForm.value['scheduledTimeFrom'],
        scheduledTimeTo: this.assignExamForm.value['scheduledTimeTo'],
        url: `http://localhost:4200/exams/${this.examToBeAssigned.id}`,
      },
      duration: this.assignExamForm.value['examDuration'],
      takenBy: this.assignExamForm.value['studentUsername'],
      createdBy: this.user.username,
    };

    this.examDataService.assignExamToStudent(
      this.examToBeAssigned.id,
      assignExamObj
    );
  }

  onClose() {
    this.close.emit(false);
  }
}
