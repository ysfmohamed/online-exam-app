import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

// Components
import { QuestionsComponent } from './questions/questions.component';
import { QuestionEditComponent } from './questions/question-edit/question-edit.component';
import { LoginComponent } from './auth/login.component';
import { SignupComponent } from './auth/signup/signup-component';
import { AuthGuardService } from './auth/auth-guard.service';
import { HomeComponent } from './home/home.component';
import { UserPageComponent } from './user-page/user-page.component';
import { CreateExamComponent } from './create-exam/create-exam.component';
import { ExamsComponent } from './exams/exams.component';
import { RunningExamComponent } from './exams/running-exam/running-exam.component';

// const appRoutes: Routes = [
//   { path: '', redirectTo: '/questions', pathMatch: 'full' },
//   {
//     path: 'questions',
//     component: QuestionsComponent,
//     children: [
//       { path: 'create', component: QuestionEditComponent },
//       { path: ':id/edit', component: QuestionEditComponent },
//     ],
//   },
// ];

const appRoutes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },

  { path: 'home', component: HomeComponent },

  { path: 'login', component: LoginComponent },

  {
    path: 'user/:id',
    component: UserPageComponent,
  },

  {
    path: 'questions-list',
    component: QuestionsComponent,
    canActivate: [AuthGuardService],
    data: {
      role: 'teacher',
    },
  },

  {
    path: 'questions/create',
    component: QuestionEditComponent,
    canActivate: [AuthGuardService],
    data: {
      role: 'teacher',
    },
  },

  {
    path: 'exam/exam-definitions-list',
    component: ExamsComponent,
    canActivate: [AuthGuardService],
    data: {
      role: 'teacher',
    },
  },

  {
    path: 'exam/create',
    component: CreateExamComponent,
    canActivate: [AuthGuardService],
    data: {
      role: 'teacher',
    },
  },

  {
    path: 'exams',
    component: RunningExamComponent,
    canActivate: [AuthGuardService],
    data: {
      role: 'student',
    },
  },

  {
    path: 'questions/:id/edit',
    component: QuestionEditComponent,
    canActivate: [AuthGuardService],
    data: {
      role: 'teacher',
    },
  },

  { path: 'signup', component: SignupComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
