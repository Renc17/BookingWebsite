import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';

import { AccountService} from '@app/_service/account.service';
import { AlertService } from '@app/_service/alert.service';
import {Role} from '@app/_models/role';
import {User} from '@app/_models/user';
import {Booking} from '@app/_models/booking';
import {RentalService} from '@app/_service/rental.service';
import {Rentals} from '@app/_models/rentals';

@Component({
  templateUrl: 'login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string;
  user: User;
  OnBookingRental: Rentals;
  BookingDetails: Booking;


  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private accountService: AccountService,
    private alertService: AlertService,
    private userService: AccountService,
    private rentalService: RentalService
  ) {
    this.rentalService.OnBookingRental.subscribe(x => this.OnBookingRental = x);
    this.rentalService.userBooking.subscribe(x => this.BookingDetails = x);
  }

  ngOnInit() {
    this.form = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  // convenience getter for easy access to form fields
  get f() { return this.form.controls; }

  onSubmit() {
    this.submitted = true;

    // reset alerts on submit
    this.alertService.clear();

    // stop here if form is invalid
    if (this.form.invalid) {
      return;
    }

    this.loading = true;
    this.accountService.login(this.form.value)
      .pipe(first())
      .subscribe(
        data => {
          this.userService.user.subscribe(x => this.user = x);

          if (this.OnBookingRental !== null) {
            this.rentalService.book(this.BookingDetails, this.OnBookingRental.rentHouse_id, this.user.id)
              .subscribe(bookingDetail => {
                console.log('bookingDetails ' + JSON.stringify(bookingDetail));
                sessionStorage.removeItem('UserBooking');
                sessionStorage.removeItem('rentals');
                sessionStorage.removeItem('OnBookingRental');
              });
          }

          const role = this.user.roles[Object.keys(this.user.roles)[0]].name;
          if (role === Role.admin){
            this.returnUrl = 'admin/profile/';
          } else {
            this.returnUrl = 'users/profile/';
          }
          this.returnUrl = this.returnUrl + this.userService.userValue.id;
          console.log(this.returnUrl);
          this.router.navigate([this.returnUrl]);

        },
        error => {
          this.alertService.error(error);
          this.loading = false;
        });
  }
}
