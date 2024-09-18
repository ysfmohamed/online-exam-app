import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { FormGroup, FormControl, FormArray } from '@angular/forms';

// Models
import { Question } from 'src/app/models/Question.model';
import { QuestionData } from '../../models/QuestionData';

// Data Services
import { QuestionDataService } from '../../data-services/question-data.service';
import { QuestionService } from 'src/app/services/question.service';

@Component({
  selector: 'app-question-edit',
  templateUrl: 'question-edit.component.html',
  styleUrls: ['question-edit.component.css'],
})
export class QuestionEditComponent implements OnInit {
  questionForm: FormGroup;
  questionToBeEdited;
  questionId;
  editMode: boolean = false;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private questionDataService: QuestionDataService,
    private questionService: QuestionService
  ) {}

  ngOnInit() {
    this.route.params.subscribe((params: Params) => {
      this.editMode = params['id'] !== undefined;
      this.questionId = params['id'];

      this.initializeForm();
    });

    if (this.editMode) {
      this.questionDataService
        .fetchQuestion(this.questionId)
        .subscribe((question: QuestionData) => {
          this.populateForm(question);
        });
    }
  }

  private buildAnswers(questionAnswers, correctAnswers) {
    const answers: { isCorrectAnswer: boolean; answerContent: string }[] = [];

    let found: boolean = false;

    for (let i = 0; i < questionAnswers.length; i++) {
      found = false;
      for (let j = 0; j < correctAnswers.length; j++) {
        if (questionAnswers[i].id === correctAnswers[j]) {
          found = true;
        }
      }
      if (found) {
        answers.push({
          isCorrectAnswer: true,
          answerContent: questionAnswers[i].content,
        });
      } else {
        answers.push({
          isCorrectAnswer: false,
          answerContent: questionAnswers[i].content,
        });
      }
    }

    return answers;
  }

  private buildAnswersFormGroup(buildedAnswer) {
    for (let answer of buildedAnswer) {
      (<FormArray>this.questionForm.get('answers')).push(
        new FormGroup({
          isCorrectAnswer: new FormControl(answer.isCorrectAnswer),
          answerContent: new FormControl(answer.answerContent),
        })
      );
    }
  }

  private populateForm(questionToBeEdited) {
    this.questionToBeEdited = questionToBeEdited;
    const correctAnswers = this.questionToBeEdited.correctAnswersIds;
    const questionAnswers = this.questionToBeEdited.answers;

    const answers = this.buildAnswers(questionAnswers, correctAnswers);
    this.buildAnswersFormGroup(answers);

    this.questionForm.patchValue({
      questionContent: this.questionToBeEdited.content,
      questionType: this.questionToBeEdited.typeId,
      questionLevel: this.questionToBeEdited.levelId,
      questionCategory: this.questionToBeEdited.category,
      questionSubCategory: this.questionToBeEdited.subCategory,
      questionMark: this.questionToBeEdited.mark,
      questionExpectedTime: this.questionToBeEdited.expectedTime,
    });
  }

  initializeForm() {
    const questionAnswers = new FormArray([]);

    this.questionForm = new FormGroup({
      questionContent: new FormControl(),
      questionType: new FormControl(),
      questionLevel: new FormControl(),
      questionCategory: new FormControl(),
      questionSubCategory: new FormControl(),
      questionMark: new FormControl(),
      questionExpectedTime: new FormControl(),
      answers: questionAnswers,
    });
  }

  onAddNewAnswer() {
    (<FormArray>this.questionForm.get('answers')).push(
      new FormGroup({
        isCorrectAnswer: new FormControl(),
        answerContent: new FormControl(),
      })
    );
  }

  onSubmit() {
    const questionToBeAdded = new Question(
      this.questionForm.value['questionContent'].trim(),
      this.questionForm.value['questionType'],
      this.questionForm.value['questionLevel'],
      this.questionForm.value['questionCategory'].trim(),
      this.questionForm.value['questionSubCategory'],
      this.questionForm.value['questionMark'],
      this.questionForm.value['questionExpectedTime'],
      [],
      '2018170484',
      new Date(Date.now()).toISOString(),
      []
    );

    // questionSubCategory may be undefined
    if (questionToBeAdded['questionSubCategory']) {
      questionToBeAdded['questionSubCategory'].trim();
    }

    const questionAnswers = this.questionForm.value['answers'];

    for (let i = 0; i < questionAnswers.length; i++) {
      if (questionAnswers[i].isCorrectAnswer === true) {
        questionToBeAdded.correctAnswersIds.push(i + 1);
      }
      questionToBeAdded.answers.push({
        id: i + 1,
        content: questionAnswers[i].answerContent.trim(),
      });
    }

    if (this.editMode) {
      questionToBeAdded.id = this.questionId;
      this.questionDataService
        .updateQuestion(this.questionId, questionToBeAdded)
        .subscribe((response) => {
          this.questionService.updateQuestion(
            this.questionId,
            questionToBeAdded
          );
        });
    } else {
      this.questionDataService.storeQuestion(questionToBeAdded);
    }

    this.router.navigate(['questions-list']);
  }

  onDeleteAnswerFromQuestion(answerId: number) {
    this.onDeleteAnswer(answerId);

    this.questionDataService
      .deleteAnswerFromQuestion(this.questionId, answerId + 1)
      .subscribe((answersList) => {
        console.log(`Answer ID: ${answerId} is deleted`);
        console.log(answersList);
      });
  }

  onAddAnswerToQuestion() {}

  onDeleteQuestion() {
    if (confirm('Are you sure you want to delete this question?')) {
      this.questionDataService
        .deleteQuestion(this.questionId)
        .subscribe((deletedQuestion) => {
          this.router.navigate(['questions-list']);
        });
    }
  }

  onDeleteAnswer(answerId: number) {
    (<FormArray>this.questionForm.get('answers')).removeAt(answerId);
  }

  getQuestionAnswers() {
    return (<FormArray>this.questionForm.get('answers')).controls;
  }
}
