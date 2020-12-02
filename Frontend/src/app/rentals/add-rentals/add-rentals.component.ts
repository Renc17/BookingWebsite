import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AlertService } from '@app/_service/alert.service';
import { RentalService } from '@app/_service/rental.service';
import { first } from 'rxjs/operators';
import { User } from '@app/_models/user';
import { AccountService } from '@app/_service/account.service';

import 'ol/ol.css';
import {fromLonLat, transform} from 'ol/proj.js';
import { marker } from '@app/_models/marker';
import Map from 'ol/Map';
import View from 'ol/View';
import Feature from 'ol/Feature';
import Vector from 'ol/source/Vector';
import Point from 'ol/geom/Point';
import { Tile as TileLayer, Vector as VectorLayer } from 'ol/layer';
import { Icon, Style } from 'ol/style';
import OSM from 'ol/source/OSM';

@Component({
  selector: 'app-add-rentals',
  templateUrl: './add-rentals.component.html',
  styleUrls: ['./add-rentals.component.css']
})
export class AddRentalsComponent implements OnInit {

  form: FormGroup;
  id: string;
  loading = false;
  submitted = false;
  returnUrl: string;
  user: User;
  rental: any;

  selectedFile: File;
  selectedInteriorFiles: FileList;
  message: string;
  imageName: any;

  mapId: string;
  map: Map = undefined;
  longitudePointer = 23.735124757612443;
  latitudePointer = 37.987236590031074;
  mapPointer = marker;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private rentalService: RentalService,
    private alertService: AlertService,
    private userService: AccountService
  ) {
    this.userService.user.subscribe(x => this.user = x);
  }


  ngOnInit(): void {

    this.map = new Map({
      target: 'map',
      layers: [
        new TileLayer({
          source: new OSM(),
        }),
      ],
      view: new View({
        center: fromLonLat([this.longitudePointer, this.latitudePointer]),
        zoom: 6,
      })
    });

    this.map.on('click', args => {
      console.log(args.coordinate);
      const lonLat = transform(args.coordinate, 'EPSG:3857', 'EPSG:4326');
      this.longitudePointer = lonLat[0];
      this.latitudePointer = lonLat[1];
      this.simpleReverseGeocoding(this.longitudePointer, this.latitudePointer);
    });

    const layer = new VectorLayer({
      source: new Vector({
        features: [
          new Feature({
            geometry: new Point(fromLonLat([this.longitudePointer, this.latitudePointer]))
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

    this.form = this.formBuilder.group({
      name: ['', Validators.required],
      location: [''],
      address: [''],
      lon:  [''],
      lat:  [''],
      description: ['', Validators.required],
      capacity: ['', Validators.required],
      minNumGuests: ['', Validators.required],
      minNumOfNights: ['', Validators.required],
      minPrice: ['', Validators.required],
      numOfBeds: ['', Validators.required],
      numOfBedrooms: ['', Validators.required],
      numOfBathrooms: ['', Validators.required],
      squareMeters: ['', Validators.required],
      costPerPerson: ['', Validators.required],
      kitchen: [false],
      freeParking: [false],
      ac: [false],
      fak: [false],
      carbonAlarm: [false],
      wifi: [false],
      hairDryer: [false],
      tv: [false],
      iron: [false],
      smokeAlarm: [false],
      selectedFile: [Validators.required],
      selectedInteriorFiles: [Validators.required]
    });

    this.returnUrl = '/users/profile/' + this.userService.userValue.id;
  }

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
    console.log('on submit data : ' + JSON.stringify(this.rental));
    this.form.value.location = this.rental.address.city;
    this.form.value.address = this.rental.display_name;
    this.form.value.lon = this.rental.lon;
    this.form.value.lat = this.rental.lat;

    this.rentalService.register(this.form.value, this.userService.userValue.id)
      .pipe(first())
      .subscribe(
        data => {
           console.log('data : ' + JSON.stringify(data));
           this.onUpload();
           this.router.navigate([this.returnUrl], { relativeTo: this.route });
        },
        error => {
          // console.log('something went wrong');
          this.alertService.error(error);
          this.loading = false;
        });
  }

  cancel() {
    this.router.navigate([this.returnUrl]);
  }

  public onFileChanged(event) {
    this.selectedFile = event.target.files[0];
  }

  public selectFiles(event) {
    this.selectedInteriorFiles = event.target.files;
  }

  onUpload() {
    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', this.selectedFile, this.selectedFile.name);
    this.rentalService.uploadMainPic(uploadImageData, this.form.value.name);

    /*const uploadInteriorImages = new FormData();
    for (let i = 0; i < this.selectedInteriorFiles.length; i++) {
      console.log('interior image uploading : ' + this.selectedInteriorFiles[i].name);
      uploadInteriorImages.append('imageFile', this.selectedInteriorFiles[i]);
    }
    this.rentalService.uploadRentalInterior(uploadInteriorImages, this.form.value.name);*/
  }

  simpleReverseGeocoding(lon, lat) {
    fetch('http://nominatim.openstreetmap.org/reverse?format=json&lon=' + lon + '&lat=' + lat).then((response) => {
      return response.json();
    }).then(data => {
      console.log('data : ' + JSON.stringify(data));
      this.rental = data;
    });
  }

}
