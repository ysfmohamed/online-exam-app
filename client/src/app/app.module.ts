import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS } from '@angular/common/http';

// Routers
import { AppRoutingModule } from './app-routing.module';

// Http
import { HttpClientModule } from '@angular/common/http';

// interceptors
import { AuthInterceptorService } from './auth/auth-interceptor.service';

//Pagination Module
import { NgxPaginationModule } from 'ngx-pagination';

// Components
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { QuestionsComponent } from './questions/questions.component';
import { QuestionListComponent } from './questions/question-list/question-list.component';
import { QuestionItemComponent } from './questions/question-list/question-item/question-item.component';
import { QuestionEditComponent } from './questions/question-edit/question-edit.component';
import { LoginComponent } from './auth/login.component';
import { SignupComponent } from './auth/signup/signup-component';
import { CreateExamComponent } from './create-exam/create-exam.component';
import { QuestionsModalComponent } from './shared/questions-modal/questions-modal.component';
import { SpinnerComponent } from './shared/spinner/spinner.component';
import { ExamsComponent } from './exams/exams.component';
import { AssignExamModalComponent } from './assign-exam-modal/assign-exam-modal.component';
import { RunningExamComponent } from './exams/running-exam/running-exam.component';

// Directives
import { AuthRoleDirective } from './auth/auth-role.directive';
import { HomeComponent } from './home/home.component';
import { UserPageComponent } from './user-page/user-page.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    LoginComponent,
    SignupComponent,
    QuestionsComponent,
    QuestionListComponent,
    QuestionItemComponent,
    QuestionEditComponent,
    HomeComponent,
    UserPageComponent,
    CreateExamComponent,
    QuestionsModalComponent,
    AssignExamModalComponent,
    SpinnerComponent,
    ExamsComponent,
    RunningExamComponent,
    AuthRoleDirective,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    NgxPaginationModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptorService,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
