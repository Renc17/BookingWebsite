import { Component, OnInit } from '@angular/core';
import {RentalService} from '@app/_service/rental.service';
import {ActivatedRoute, Router} from '@angular/router';
import {AlertService} from '@app/_service/alert.service';
import {DomSanitizer} from '@angular/platform-browser';
import {DefaultProfilePic} from '@app/_models/defaultProfilePic';

@Component({
  selector: 'app-get-rentals',
  templateUrl: './rentalSearch.component.html',
  styleUrls: ['./rentalSearch.component.css']
})
export class RentalSearchComponent implements OnInit {

  rentals = null;
  returnUrl: string;
  booking = null;

  retrievedImage: any;

  constructor(private rentalService: RentalService,
              private route: ActivatedRoute,
              private router: Router,
              private alertService: AlertService,
              private sanitizer: DomSanitizer) {
    this.rentalService.userBooking.subscribe(x => this.booking = x);
  }

  ngOnInit(): void {
    this.rentalService.rental.subscribe(x => this.rentals = x);
    console.log('All rentals list : ' + JSON.stringify(this.rentals));
    this.returnUrl = '/rentals/profile/';
    this.retrievedImage = 'data:image/jpeg;base64, ' + DefaultProfilePic;
  }

  getRental(id: string) {
    this.rentalService.getById(id)
      .subscribe(data => {
        // console.log('Subscription data id : ' + JSON.stringify(data));
        this.returnUrl = this.returnUrl + data.rentHouse_id;
        console.log(this.returnUrl);
        this.router.navigate([this.returnUrl], {relativeTo: this.route});
      }, error => console.log(error));
  }

}
