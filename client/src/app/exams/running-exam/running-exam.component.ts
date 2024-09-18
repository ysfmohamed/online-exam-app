import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormArray } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { RunningExamService } from 'src/app/services/running-exam.service';
import { concatMap, tap } from 'rxjs';
import { ExamDataService } from 'src/app/data-services/exam-data.service';

interface Answer {
  questionId: string;
  selectedAnswersIds: number[];
  answerTime: number;
  displayTime: number;
  completionTime?: number;
}

@Component({
  selector: 'running-exam-app',
  templateUrl: './running-exam.component.html',
  styleUrls: ['./running-exam.component.css'],
})
export class RunningExamComponent implements OnInit {
  answerForm: FormGroup;

  isStartExamPressed: boolean = false;

  questionsNum: number;
  questionNum: number = 0;
  questionToBeDisplayed;
  correctAnswersNum;
  questionAnswers;

  isExamLoading: boolean = true;
  isQuestionLoaded = false;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private runningExamService: RunningExamService,
    private examDataService: ExamDataService
  ) {}

  ngOnInit() {
    this.runningExamService
      .handleExam(this.route)
      .subscribe((examDefinition) => {
        this.isExamLoading = false;
        this.questionsNum = examDefinition['questions'].length;
      });
  }

  private initializeAnswerForm() {
    this.answerForm = new FormGroup({
      answers: new FormArray([]),
    });
  }

  private buildAnswersFromGroup(answers) {
    for (let answer of answers) {
      (<FormArray>this.answerForm.get('answers')).push(
        new FormGroup({
          isAnswerChoosen: new FormControl(),
        })
      );
    }
  }

  getAnswers() {
    return (<FormArray>this.answerForm.get('answers')).controls;
  }

  onStartExam() {
    this.isStartExamPressed = true;

    const firstQuestionId =
      this.runningExamService.examDefinition['questions'][0];

    let startedTime = new Date().getTime();
    const duration = +this.runningExamService.examInstance['duration'];
    const endTime = startedTime + duration * 60 * 1000;

    const body = {
      startedTime: startedTime,
      endTime: endTime,
      status: 'taken',
    };

    this.examDataService
      .startExam(this.runningExamService.examInstanceId, body)
      .pipe(concatMap(() => this.populateFirstQuestion(firstQuestionId)))
      .subscribe((question) => {
        this.initializeAnswerForm();
        this.isQuestionLoaded = true;
        this.questionToBeDisplayed = question;
        this.questionAnswers = question['answers'];
        this.buildAnswersFromGroup(question['answers']);
        this.correctAnswersNum = question['correctAnswersIds'].length;
      });
  }

  onNext() {
    this.isQuestionLoaded = false;

    this.questionNum += 1;
    const nextQuestionId =
      this.runningExamService.examDefinition['questions'][this.questionNum];

    const answer = this.buildChoosenAnswer(false);

    this.sendAnswer(this.runningExamService.examInstanceId, answer)
      .pipe(concatMap(() => this.loadNextQuestion(nextQuestionId)))
      .subscribe((nextQuestion) => {
        this.initializeAnswerForm();

        this.isQuestionLoaded = true;
        this.questionToBeDisplayed = nextQuestion;
        this.questionAnswers = nextQuestion['answers'];
        this.buildAnswersFromGroup(nextQuestion['answers']);
        this.correctAnswersNum = nextQuestion['correctAnswersIds'].length;
      });
  }

  private populateFirstQuestion(questionId) {
    return this.runningExamService.populateQuestion(questionId);
  }

  private buildChoosenAnswer(isSubmitMode: boolean) {
    let choosenAnswers: number[] = [];
    this.getAnswers().forEach((answer, index) => {
      if (answer.value.isAnswerChoosen === true) {
        choosenAnswers.push(index + 1);
        console.log(choosenAnswers);
      }
    });

    let answer: Answer = {
      questionId: this.questionToBeDisplayed['id'],
      selectedAnswersIds: choosenAnswers,
      answerTime: new Date().getTime(),
      displayTime: this.questionToBeDisplayed['expectedTime'],
      completionTime: isSubmitMode ? new Date().getTime() : null,
    };

    return answer;
  }

  private sendAnswer(examInstanceId: number, answer: Answer) {
    return this.examDataService.next(examInstanceId, answer);
  }

  private loadNextQuestion(nextQuestionId: string) {
    return this.runningExamService.populateQuestion(nextQuestionId);
  }

  onSubmit() {
    const answer = this.buildChoosenAnswer(true);

    this.sendAnswer(this.runningExamService.examInstanceId, answer).subscribe(
      (response) => {
        console.log(response);

        this.router.navigate(['../']);
      }
    );
  }
}
