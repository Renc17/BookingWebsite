import { Component, OnInit } from '@angular/core';
import {RentalService} from '@app/_service/rental.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Rentals} from '@app/_models/rentals';
import {AccountService} from '@app/_service/account.service';
import {User} from '@app/_models/user';
import {first} from 'rxjs/operators';
import {AlertService} from '@app/_service/alert.service';
import {Booking} from '@app/_models/booking';
import {DomSanitizer} from '@angular/platform-browser';

import 'ol/ol.css';
import {fromLonLat} from 'ol/proj.js';
import {marker} from '@app/_models/marker';
import Map from 'ol/Map';
import View from 'ol/View';
import Feature from 'ol/Feature';
import Vector from 'ol/source/Vector';
import Point from 'ol/geom/Point';
import { Tile as TileLayer, Vector as VectorLayer } from 'ol/layer';
import { Icon, Style } from 'ol/style';
import OSM from 'ol/source/OSM';

@Component({
  selector: 'app-rental-profile',
  templateUrl: './rental-profile.component.html',
  styleUrls: ['./rental-profile.component.css']
})
export class RentalProfileComponent implements OnInit {

  rental: Rentals;
  recommendations: Rentals[];
  owner: User;
  loading = false;
  submitted = false;
  returnUrl: string;
  id: string;
  ownerId: string;
  user: User;
  comments = null;
  bookingParams: Booking;

  totalPrice: string;
  stayingDuration: string;
  costByNumGuests: string;
  bookingDetails: any;

  map: Map;
  mapPointer = marker;
  address: any;

  retrievedImage: any;

  constructor(private rentalService: RentalService,
              private userService: AccountService,
              private alertService: AlertService,
              private router: Router,
              private route: ActivatedRoute,
              private sanitizer: DomSanitizer) {

    this.userService.user.subscribe(x => this.user = x);
    this.rentalService.userBooking.subscribe(x => this.bookingParams = x);
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params.id;

    this.rentalService.getById(this.id)
      .subscribe(data => {
        this.rental = data;
      }, error => console.log(error));

    this.rentalService.OnBookingRental.subscribe(x => this.rental = x);
    console.log('rental loaded : ' + JSON.stringify(this.rental));

    this.rentalService.getOwnerByRentalId(this.id)
      .subscribe(data => {
        // console.log('Subscription data for owner : ' + JSON.stringify(data));
        this.owner = data;
      }, error => console.log(error));

    this.rentalService.getBookingDetails(this.bookingParams, this.id)
      .pipe(first())
      .subscribe(x => {
        this.stayingDuration = x.duration;
        this.totalPrice = x.price;
        this.costByNumGuests = x.costByNumGuests;
        this.bookingDetails = x;
        console.log('BookingDetails : ' + JSON.stringify(this.bookingDetails));
      }, error => console.log(error));

    this.getAllComments();
    if (this.userService.isLoggedIn()){
      this.getRecommendations();
    }
    // this.getRentalPics();
    // this.getInteriorPics();

    console.log('map create... lon : ' + this.rental.lon + 'lat : ' + this.rental.lat);
    this.map = new Map({
      target: 'map',
      layers: [
        new TileLayer({
          source: new OSM(),
        }),
      ],
      view: new View({
        center: fromLonLat([this.rental.lon, this.rental.lat]),
        zoom: 14,
      })
    });

    const layer = new VectorLayer({
      source: new Vector({
        features: [
          new Feature({
            geometry: new Point(fromLonLat([this.rental.lon, this.rental.lat]))
          })
        ]
      }),
      target: this.map,
      style: new Style({
        image: new Icon({
          anchor: [0.5, 1],
          anchorXUnits: 'fraction',
          anchorYUnits: 'fraction',
          scale: 0.05,
          anchorOrigin: 'top-left',
          src: this.mapPointer,
        }),
      })
    });

    this.map.addLayer(layer);
  }


  OnSubmit() {
    this.submitted = true;

    // reset alerts on submit
    this.alertService.clear();

    if (!this.userService.isLoggedIn()){
      this.returnUrl = 'account/login';
      this.router.navigate([this.returnUrl]);
      return;
    }

    this.loading = true;

    this.rentalService.book(this.bookingParams, this.rental.rentHouse_id, this.user.id)
      .pipe(first())
      .subscribe(
        data => {
          this.returnUrl = 'users/profile/';
          this.returnUrl = this.returnUrl + this.userService.userValue.id;
          sessionStorage.removeItem('UserBooking');
          sessionStorage.removeItem('rentals');
          sessionStorage.removeItem('OnBookingRental');
          this.router.navigate([this.returnUrl]);

        },
        error => {
          this.alertService.error(error);
          this.loading = false;
        });
  }

  getAllComments() {
    console.log('get Comments...');
    this.rentalService.getComments(this.id)
      .subscribe(data => {
          this.comments = data;
      });
  }

  getRentalPics(){
    console.log('getProfilePic...');
    this.rentalService.downloadMainPic(this.rental.rentHouse_id)
      .subscribe(res => {
        console.log('res : ' + JSON.stringify(res));
        this.retrievedImage = this.sanitizer.bypassSecurityTrustUrl('data:image/jpeg;base64, ' + res.picByte);
      });
  }

  getInteriorPics(){
    console.log('getInteriorPics...');
    this.rentalService.downloadInteriorPic(this.id)
      .subscribe(res => {
        console.log('res from getInterior : ' + JSON.stringify(res));
        this.retrievedImage = this.sanitizer.bypassSecurityTrustUrl('data:image/jpeg;base64, ' + res.picByte);
      });
  }

  getRecommendations() {
    console.log('getRecommendations...');
    this.rentalService.getRecommendations(this.userService.userValue.id)
      .subscribe(res => {
        console.log('res from getRecommendations : ' + JSON.stringify(res));
        this.recommendations = res;
      });
  }

}
