import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {AlertService} from '@app/_service/alert.service';
import {filter, first} from 'rxjs/operators';
import {RentalService} from '@app/_service/rental.service';

@Component({
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  form: FormGroup;
  loading = false;
  submitted = false;
  rentals = null;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private rentalService: RentalService,
    private alertService: AlertService
  ) { }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      location: ['', Validators.required],
      checkin: ['', Validators.required],
      checkout: ['', Validators.required],
      guests: ['', Validators.required]
      });
  }

  get f() { return this.form.controls; }

  OnSubmit() {
    this.submitted = true;

    // reset alerts on submit
    this.alertService.clear();

    // stop here if form is invalid
    if (this.form.invalid) {
      console.log('invalid form');
      return;
    }

    this.loading = true;
    // console.log('Search Submitted, everything is good to go');

    console.log('form : ' + JSON.stringify(this.form.value));

    this.rentalService.getFilterRents(this.form.value)
      .pipe(first())
      .subscribe(
        data => {
          this.router.navigate(['../rentals/getRentals'], { relativeTo: this.route });
        }
      );
  }
}
