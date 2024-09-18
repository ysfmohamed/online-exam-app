import { Component, OnInit } from '@angular/core';
import { AuthDataService } from '../data-services/auth-data.service';
import { AuthenticatedUser } from '../models/AuthenticatedUser.model';

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css'],
})
export class UserPageComponent implements OnInit {
  user: AuthenticatedUser;

  constructor(private authDataService: AuthDataService) {}

  ngOnInit() {
    this.authDataService.authenticatedUser.subscribe((authenticatedUser) => {
      if (authenticatedUser) {
        this.user = authenticatedUser;
      }
    });
  }
}
