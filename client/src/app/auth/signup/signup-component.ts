import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';

import { User } from '../../models/User.model';
import { AuthDataService } from '../../data-services/auth-data.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup-component.html',
  styleUrls: ['./signup-component.css'],
})
export class SignupComponent implements OnInit {
  signupForm: FormGroup;
  createdUserId: string;

  constructor(private authDataService: AuthDataService) {}

  ngOnInit() {
    this.initializeForm();
  }

  initializeForm() {
    this.signupForm = new FormGroup({
      username: new FormControl(),
      firstName: new FormControl(),
      lastName: new FormControl(),
      email: new FormControl(),
      password: new FormControl(),
      userRole: new FormControl(),
    });
  }

  onSubmit() {
    console.log('sign up submit');

    const user: User = new User(
      this.signupForm.value['username'],
      this.signupForm.value['firstName'],
      this.signupForm.value['lastName'],
      this.signupForm.value['email'],
      this.signupForm.value['password'],
      this.signupForm.value['userRole']
    );

    this.authDataService.signup(user).subscribe((response) => {
      this.createdUserId = response['userId'];
    });
  }
}
