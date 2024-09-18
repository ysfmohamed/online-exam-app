import {
  Component,
  OnInit,
  Input,
  OnChanges,
  SimpleChanges,
} from '@angular/core';
import { AuthDataService } from '../data-services/auth-data.service';
import { AuthenticatedUser } from '../models/AuthenticatedUser.model';
import { NotificationsDataService } from '../data-services/notifications-data.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit, OnChanges {
  @Input() user: AuthenticatedUser = null;
  notifications;

  constructor(
    private authDataService: AuthDataService,
    private notificationsDataService: NotificationsDataService
  ) {}

  ngOnInit() {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.user.currentValue) {
      this.fetchUserNotifications(this.user.username);
    }
  }

  fetchUserNotifications(username: string) {
    this.notificationsDataService
      .fetchUserNotifications(username)
      .subscribe((userNotifications) => {
        this.notifications = userNotifications['userExams'];
      });
  }

  onLogout() {
    this.authDataService.logout(this.user.userId);
  }
}
