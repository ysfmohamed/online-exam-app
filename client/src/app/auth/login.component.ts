import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormControl } from '@angular/forms';

import { AuthDataService } from '../data-services/auth-data.service';

@Component({
  selector: 'app-login',
  templateUrl: './login-component.html',
  styleUrls: ['./login-component.css'],
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  loggedInUser;

  constructor(
    private authDataService: AuthDataService,
    private router: Router
  ) {}

  ngOnInit() {
    this.loginForm = new FormGroup({
      username: new FormControl(),
      password: new FormControl(),
    });
  }

  onSubmit() {
    const username = this.loginForm.value['username'];
    const password = this.loginForm.value['password'];

    this.authDataService.login(username, password).subscribe((response) => {
      this.loggedInUser = response;
    });
  }
}
