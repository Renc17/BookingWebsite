import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AccountService} from '@app/_service/account.service';
import {AlertService} from '@app/_service/alert.service';
import {RentalService} from '@app/_service/rental.service';

@Component({
  selector: 'app-get-rentals-by-owner',
  templateUrl: './get-rentals-by-owner.component.html',
  styleUrls: ['./get-rentals-by-owner.component.css']
})
export class GetRentalsByOwnerComponent implements OnInit {

  id: string;
  rentals = null;
  returnUrl: string;

  constructor(private route: ActivatedRoute, private router: Router,
              private userService: AccountService,
              private rentalService: RentalService,
              private alertService: AlertService) {
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params.id;

    this.rentalService.getRentalsByOwner(this.id)
      .subscribe(data => {
        console.log('Subscription data id : ' + JSON.stringify(data));
        this.rentals = data;
      }, error => console.log(error));
  }

  Back(): void{
    this.returnUrl = 'admin/profile/';
    this.returnUrl = this.returnUrl + this.userService.userValue.id;
    console.log(this.returnUrl);
    this.router.navigate([this.returnUrl]);

  }

}
