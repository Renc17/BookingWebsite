import { Component, OnInit } from '@angular/core';
import {User} from '@app/_models/user';
import {ActivatedRoute, Router} from '@angular/router';
import { AccountService } from '@app/_service/account.service';
import {first} from 'rxjs/operators';
import {AlertService} from '@app/_service/alert.service';
import {RentalService} from '@app/_service/rental.service';
import {DomSanitizer} from '@angular/platform-browser';
import { FormBuilder, FormGroup } from '@angular/forms';
import {DefaultProfilePic} from '@app/_models/defaultProfilePic';
import {AppComponent} from '@app/app.component';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  form: FormGroup;
  user: User;
  rentals = null;
  bookedRentalInfo: any;
  reservations = null;
  id: string;
  returnUrl: string;
  updateRentalUrl: string;

  isShown = true;
  isShownReservations = true;
  isShownRentals = true;

  retrievedImage: any;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private formBuilder: FormBuilder,
              private userService: AccountService,
              private rentalService: RentalService,
              private alertService: AlertService,
              private sanitizer: DomSanitizer,
              private app: AppComponent) {
    this.userService.user.subscribe(x => this.user = x);
  }

  ngOnInit(): void {
    this.getProfilePic();
    this.app.updateProfileIcon();
    this.getRentals();
    this.getReservations();
    console.log('param id of user : ' + this.userService.userValue.id);

    this.form = this.formBuilder.group({
      comment: ['']
    });
  }

  get f() { return this.form.controls; }

  logout() {
    this.userService.logout();
  }

  deleteUser(id: string) {
    this.userService.delete(id)
      .pipe(first())
      .subscribe(data => {
          this.returnUrl = 'account/login';
          this.router.navigate([this.returnUrl]);
        },
        error => {
          this.alertService.error(error);
        });
  }

  edit() {
    this.returnUrl = 'users/edit/';
    this.returnUrl = this.returnUrl + this.userService.userValue.id;
    console.log(this.returnUrl);
    this.router.navigate([this.returnUrl]);
  }

  toggleSettings() {
    this.isShown = !this.isShown;
  }
  toggleReservations() {
    this.isShownReservations = !this.isShownReservations;
  }
  toggleRentals() {
    this.isShownRentals = !this.isShownRentals;
  }

  addRentals() {
    this.returnUrl = 'rentals/register';
    console.log(this.returnUrl);
    this.router.navigate([this.returnUrl]);
  }

  getRentals() {
    console.log('userValue : ' + this.userService.userValue);
    this.rentalService.getRentalsByOwner(this.userService.userValue.id)
      .pipe(first())
      .subscribe(rentals => this.rentals = rentals);
  }

  getReservations() {
    this.userService.getMyReservations(this.userService.userValue.id)
      .pipe(first())
      .subscribe(reservations => {
        this.reservations = reservations;
        console.log('reservation : ' + JSON.stringify(reservations));
      });
  }

  getRentalByReservation(reservationId: string) {
    console.log('reservation id : ' + reservationId);
    this.rentalService.getByReservation(reservationId)
      .pipe(first())
      .subscribe(rental => {
        console.log('bookedRental : ' + JSON.stringify(rental));
        this.bookedRentalInfo = rental;
      });
  }

  addComment(id: string) {
    this.rentalService.addComment(this.form.value.comment, id, this.userService.userValue.id)
      .subscribe(data => {
        console.log('comment : ' + JSON.stringify(data));
      });
  }

  update(id: string){
    this.updateRentalUrl = 'rentals/update/' + id;
    console.log('updateRentalUrl : ' + this.updateRentalUrl);
    this.router.navigate([this.updateRentalUrl]);
  }

  deleteRentalById(id: string) {
    this.rentalService.deleteRental(id)
      .pipe(first())
      .subscribe(data => {
        console.log('data from delete : ' + data);
        this.rentals.splice(this.rentals.indexOf(id), 1);
        },
        error => {
          this.alertService.error(error);
        });
  }

  cancelReservation(id: string) {
    this.rentalService.cancelReservation(id)
      .pipe(first())
      .subscribe(data => {
          console.log('data from delete : ' + data);
          this.reservations.splice(this.reservations.indexOf(id), 1);
        },
        error => {
          this.alertService.error(error);
        });
  }

  getProfilePic(){
    console.log('getProfilePic...');
    this.userService.download(this.userService.userValue.id)
      .subscribe(res => {
        if (res === null) {
          this.retrievedImage = this.sanitizer.bypassSecurityTrustUrl('data:image/jpeg;base64, ' + DefaultProfilePic);
        }else {
          this.retrievedImage = this.sanitizer.bypassSecurityTrustUrl('data:image/jpeg;base64, ' + res.picByte);
        }
      });
  }

  book() {
    this.returnUrl = '';
    this.router.navigate([this.returnUrl]);
  }

}
