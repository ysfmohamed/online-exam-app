import { Injectable, inject } from '@angular/core';
import {
  Router,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  CanActivateFn,
} from '@angular/router';
import { AuthDataService } from '../data-services/auth-data.service';
import { take, map } from 'rxjs';
import { AuthenticatedUser } from '../models/AuthenticatedUser.model';

@Injectable({ providedIn: 'root' })
class PermissionsService {
  constructor(
    private authDataService: AuthDataService,
    private router: Router
  ) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    return this.authDataService.authenticatedUser.pipe(
      take(1),
      map((user) => {
        const isAuth = !!user;

        if (isAuth && this.checkUserRole(user, route.data.role) > -1) {
          return true;
        }

        console.log(
          "You are not authenticated or you don't have the right roles"
        );
        return this.router.createUrlTree(['/login']);
      })
    );
  }

  private checkUserRole(authUser: AuthenticatedUser, userRole: string) {
    return authUser.roles.findIndex((role) => role === userRole);
  }
}

export const AuthGuardService: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
) => {
  return inject(PermissionsService).canActivate(route, state);
};
