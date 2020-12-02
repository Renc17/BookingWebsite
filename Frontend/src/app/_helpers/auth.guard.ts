import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, CanLoad, Route, Router, RouterStateSnapshot, UrlSegment} from '@angular/router';

import {AccountService} from '@app/_service/account.service';
import {Observable} from 'rxjs';
import {Role} from '@app/_models/role';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate, CanLoad{
  constructor(
    private router: Router,
    private accountService: AccountService
  ) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const user = this.accountService.userValue;
    if (user) {
      return true;
    }

    console.log('CanActivate is false for user : ' + user.username);
    // not logged in so redirect to login page with the return url
    this.router.navigate(['/account/login'], { queryParams: { returnUrl: state.url }});
    return false;
  }

  canLoad(route: Route, segments: UrlSegment[]): Observable<boolean> | Promise<boolean> | boolean {
    const user = this.accountService.userValue;
    if (user.roles[Object.keys(user.roles)[0]].name === Role.admin) {
      return segments.toString() === 'admin,profile,' + user.id;
    } else if (user.roles[Object.keys(user.roles)[0]].name === Role.user){
      return segments.toString() === 'users,profile,' + user.id;
    }
  }

}
