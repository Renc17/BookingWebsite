import { Component, OnInit } from '@angular/core';
import { first } from 'rxjs/operators';

import { AccountService } from '@app/_service/account.service';
import {ActivatedRoute, Router} from '@angular/router';
import {User} from '@app/_models/user';
import {AlertService} from '@app/_service/alert.service';
import {DefaultProfilePic} from '@app/_models/defaultProfilePic';
import {DomSanitizer} from '@angular/platform-browser';
import {AppComponent} from '@app/app.component';

@Component({
  selector: 'app-profile',
  templateUrl: './adminprofile.component.html',
  styleUrls: ['./adminprofile.component.css']
})
export class AdminprofileComponent implements OnInit {

  id: string;
  admin: User;
  users = null;
  returnUrl: string;
  isShown = true;

  retrievedImage: any;
  defaultPic = DefaultProfilePic;

  constructor(private route: ActivatedRoute, private router: Router,
              private userService: AccountService,
              private alertService: AlertService,
              private sanitizer: DomSanitizer,
              private app: AppComponent) {
     this.userService.user.subscribe(x => this.admin = x);
  }

  ngOnInit() {
    this.id = this.route.snapshot.params.id;    // i can user this.admin since is user data stored from login in sessionStorage

    this.userService.getById(this.id)           // -||-
      .subscribe(data => {
        console.log(data);
        console.log(this.id);
        this.admin = data;
      }, error => console.log(error));

    this.reloadData();
    this.getProfilePic();
    this.app.updateProfileIcon();
  }

  reloadData(){
    this.userService.getAll()
      .pipe(first())
      .subscribe(users => {
        this.users = users;
      });
  }

  logout() {
    this.userService.logout();
  }

  edit() {
    this.returnUrl = 'admin/edit/';
    this.returnUrl = this.returnUrl + this.id;
    console.log(this.returnUrl);
    this.router.navigate([this.returnUrl]);
  }

  deleteUser(id: string) {
    this.userService.delete(id)
      .pipe(first())
      .subscribe(data => {
          // console.log('data from delete : ' + data);
          this.users.splice(this.users.indexOf(id), 1);
        },
        error => {
          this.alertService.error(error);
        });
  }

  toggleSettings() {
    this.isShown = !this.isShown;
  }

  getRentalsByOwner(id: string) {
    this.returnUrl = 'admin/rentalByOwner/';
    this.returnUrl = this.returnUrl + id;
    console.log(this.returnUrl);
    this.router.navigate([this.returnUrl]);
  }

  getProfilePic(){
    this.userService.download(this.userService.userValue.id)
      .subscribe(res => {
        if (res === null) {
          this.retrievedImage = this.sanitizer.bypassSecurityTrustUrl('data:image/jpeg;base64, ' + DefaultProfilePic);
        }else {
          this.retrievedImage = this.sanitizer.bypassSecurityTrustUrl('data:image/jpeg;base64, ' + res.picByte);
        }
      });
  }
}
