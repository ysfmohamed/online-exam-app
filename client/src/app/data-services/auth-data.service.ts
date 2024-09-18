import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { User } from '../models/User.model';
import { BehaviorSubject, tap } from 'rxjs';
import { AuthenticatedUser } from '../models/AuthenticatedUser.model';
import { TokenExpirationValidator } from '../auth/token-exp-validator.service';

export interface AuthResponseData {
  access_token: string;
  expires_in: string;
  id_token: string;
  refresh_token: string;
  refresh_expires_in: string;
  session_state: string;
}

@Injectable({ providedIn: 'root' })
export class AuthDataService {
  authenticatedUser = new BehaviorSubject<AuthenticatedUser>(null);
  logoutTimer: any;

  constructor(private http: HttpClient, private router: Router) {}

  signup(user: User) {
    return this.http.post('http://localhost:8082/auth/signup', user);
  }

  login(username, password) {
    const body = new HttpParams()
      .set('username', username)
      .set('password', password);

    return this.http
      .post<AuthResponseData>(
        'http://localhost:8082/auth/login',
        body.toString(),
        {
          headers: new HttpHeaders().set(
            'Content-Type',
            'application/x-www-form-urlencoded'
          ),
        }
      )
      .pipe(
        tap((response) => {
          const userData = this.extractUserInfo(response);

          this.authenticatedUser.next(userData);
          localStorage.setItem('userData', JSON.stringify(userData));
          this.autoLogout(+response.expires_in * 1000);
          this.router.navigate(['/user', userData.userId]);
        })
      );
  }

  autoLogin() {
    const userData = JSON.parse(localStorage.getItem('userData'));

    if (!userData) {
      return;
    }

    const loadedUser = new AuthenticatedUser(
      userData._userId,
      userData._username,
      userData._firstName,
      userData._lastName,
      userData._email,
      userData._roles,
      userData._token,
      userData._tokenExpiresIn,
      userData._refreshToken,
      userData._refreshTokenExpiresIn
    );

    const isTokenExpired = TokenExpirationValidator.validateTokenExpirationDate(
      loadedUser.tokenExpiresIn
    );

    if (isTokenExpired) {
      this.authenticatedUser.next(loadedUser);
      const remainingTime =
        new Date(loadedUser.tokenExpiresIn).getTime() - new Date().getTime();
      this.autoLogout(remainingTime);
    }
  }

  logout(userId: string) {
    this.http
      .post(`http://localhost:8082/auth/logout/${userId}`, null)
      .subscribe((response) => {
        localStorage.removeItem('userData');
        this.authenticatedUser.next(null);
        if (this.logoutTimer) {
          clearTimeout(this.logoutTimer);
        }
        this.router.navigate(['/home']);
      });
  }

  private autoLogout(expirationDuration) {
    this.logoutTimer = setTimeout(() => {
      const userId = this.authenticatedUser.value.userId;
      this.logout(userId);
    }, expirationDuration);
  }

  private extractUserInfo(
    authResponseData: AuthResponseData
  ): AuthenticatedUser {
    const token = authResponseData.access_token;
    const userInfo = JSON.parse(atob(token.split('.')[1]));

    const userId = userInfo['sub'];
    const username = userInfo['preferred_username'];
    const userFirstName = userInfo['given_name'];
    const userLastName = userInfo['family_name'];
    const email = userInfo['email'];
    const roles = userInfo['realm_access']['roles'];

    const expiresIn: Date = new Date(
      new Date().getTime() + +authResponseData.expires_in * 1000
    );

    const refreshExpiresIn: Date = new Date(
      new Date().getTime() + +authResponseData.refresh_expires_in * 1000
    );

    return new AuthenticatedUser(
      userId,
      username,
      userFirstName,
      userLastName,
      email,
      roles,
      authResponseData.access_token,
      expiresIn,
      authResponseData.refresh_token,
      refreshExpiresIn
    );
  }
}
