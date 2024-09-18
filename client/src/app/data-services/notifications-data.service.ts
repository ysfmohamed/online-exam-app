import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class NotificationsDataService {
  constructor(private http: HttpClient) {}

  fetchUserNotifications(username: string) {
    return this.http.get('http://localhost:8084/message/user-exams', {
      params: new HttpParams().set('username', username),
    });
  }
}
