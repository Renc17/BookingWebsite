import {Component, OnInit} from '@angular/core';

import { AccountService } from './_service/account.service';
import { User } from './_models/user';
import { Router } from '@angular/router';
import {Role} from '@app/_models/role';
import {DefaultProfilePic} from '@app/_models/defaultProfilePic';
import {DomSanitizer} from '@angular/platform-browser';

// tslint:disable-next-line:component-selector
@Component({ selector: 'app', templateUrl: 'app.component.html' })
export class AppComponent implements OnInit{
  user: User;
  returnUrl: any;
  retrievedImage: any;

  constructor(private accountService: AccountService,
              private router: Router,
              private sanitizer: DomSanitizer) {
    this.accountService.user.subscribe(x => this.user = x);
  }

  ngOnInit(): void {
    sessionStorage.removeItem('UserBooking');
    sessionStorage.removeItem('rentals');
    sessionStorage.removeItem('OnBookingRental');
    this.updateProfileIcon();
  }

  public updateProfileIcon(){
    if (this.user) {
      this.accountService.download(this.accountService.userValue.id)
        .subscribe(res => {
          if (res === null) {
            this.retrievedImage = this.sanitizer.bypassSecurityTrustUrl('data:image/jpeg;base64, ' + DefaultProfilePic);
          } else {
            this.retrievedImage = this.sanitizer.bypassSecurityTrustUrl('data:image/jpeg;base64, ' + res.picByte);
          }
        });
    }
  }

  visitProfile(){
    const role = this.user.roles[Object.keys(this.user.roles)[0]].name;
    if (role === Role.admin){
      this.returnUrl = 'admin/profile/';
    } else {
      this.returnUrl = 'users/profile/';
    }
    this.returnUrl = this.returnUrl + this.accountService.userValue.id;
    console.log(this.returnUrl);
    this.router.navigate([this.returnUrl]);
  }
}
