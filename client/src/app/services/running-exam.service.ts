import { Injectable } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { concatMap, tap } from 'rxjs';
import { ExamDataService } from '../data-services/exam-data.service';
import { QuestionDataService } from '../data-services/question-data.service';

@Injectable({ providedIn: 'root' })
export class RunningExamService {
  private currentExamInstance;
  private currentExamDefinition;
  private currentExamInstanceId: number;
  private currentExamDefinitionId: number;

  private _questionsNum: number;
  private _questionNum: number = 0;
  private _questionToBeDisplayed;
  private _correctAnswersNum;

  constructor(
    private examDataService: ExamDataService,
    private questionDataService: QuestionDataService
  ) {}

  handleExam(route: ActivatedRoute) {
    return this.fetchCurrentExamInstanceId(route).pipe(
      concatMap(
        (params) => (this.currentExamInstanceId = params['examInstanceId'])
      ),
      concatMap(() =>
        this.fetchCurrentExamInstance(this.currentExamInstanceId)
      ),
      concatMap(() => this.fetchExamDefinition(this.currentExamDefinitionId))
    );
  }

  private fetchCurrentExamInstanceId(route: ActivatedRoute) {
    return route.queryParams;
  }

  private fetchCurrentExamInstance(id: number) {
    return this.examDataService.fetchExamInstance(id).pipe(
      tap((examInstance) => {
        this.currentExamInstance = examInstance;
        this.currentExamDefinitionId = examInstance['examDefinitionId'];
      })
    );
  }

  private fetchExamDefinition(id: number) {
    return this.examDataService.fetchExamDefinition(id).pipe(
      tap((examDefinition) => {
        this.currentExamDefinition = examDefinition;
      })
    );
  }

  populateQuestion(id: string) {
    return this.questionDataService.fetchQuestion(id);
  }

  public get examInstanceId() {
    return this.currentExamInstanceId;
  }

  public get examDefinitionId() {
    return this.currentExamDefinitionId;
  }

  public get examInstance() {
    return this.currentExamInstance;
  }

  public get examDefinition() {
    return this.currentExamDefinition;
  }

  public get questionsNum() {
    return this._questionsNum;
  }

  public get questionNum() {
    return this._questionNum;
  }

  public get questionToBeDisplayed() {
    return this._questionToBeDisplayed;
  }

  public get correctAnswersNum() {
    return this._correctAnswersNum;
  }
}
