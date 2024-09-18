import { Component, OnInit } from '@angular/core';
import { AuthDataService } from './data-services/auth-data.service';
import { AuthenticatedUser } from './models/AuthenticatedUser.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  user: AuthenticatedUser = null;
  title = 'client';

  constructor(private authDataService: AuthDataService) {}

  ngOnInit() {
    this.authDataService.authenticatedUser.subscribe((authenticatedUser) => {
      this.user = authenticatedUser;
    });
    this.authDataService.autoLogin();
  }
}
