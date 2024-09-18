import { Injectable } from '@angular/core';
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpHeaders,
} from '@angular/common/http';
import { pipe, take, exhaustMap } from 'rxjs';
import { AuthDataService } from 'src/app/data-services/auth-data.service';

@Injectable()
export class AuthInterceptorService implements HttpInterceptor {
  constructor(private authDataService: AuthDataService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    return this.authDataService.authenticatedUser.pipe(
      take(1),
      exhaustMap((user) => {
        if (!user) {
          return next.handle(req);
        }

        const token = 'Bearer ' + user.token;
        const modifiedReq = req.clone({
          headers: new HttpHeaders().set('Authorization', token),
        });

        return next.handle(modifiedReq);
      })
    );
  }
}
