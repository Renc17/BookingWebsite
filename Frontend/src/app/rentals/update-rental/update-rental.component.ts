import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AccountService} from '@app/_service/account.service';
import {Rentals} from '@app/_models/rentals';
import {RentalService} from '@app/_service/rental.service';
import {ActivatedRoute, Router} from '@angular/router';
import {first} from 'rxjs/operators';
import {AlertService} from '@app/_service/alert.service';

@Component({
  selector: 'app-update-rental',
  templateUrl: './update-rental.component.html',
  styleUrls: ['./update-rental.component.css']
})
export class UpdateRentalComponent implements OnInit {

  form: FormGroup;
  user = null;
  returnUrl: string;
  rental: Rentals;
  id: string;

  isAddMode: boolean;
  loading = false;
  submitted = false;

  selectedFile: File;
  message: string;
  imageName: any;

  constructor(private formBuilder: FormBuilder,
              private userService: AccountService,
              private route: ActivatedRoute,
              private router: Router,
              private rentalService: RentalService,
              private alertService: AlertService) {
    this.userService.user.subscribe(x => this.user = x);
  }

  ngOnInit(): void {

    this.id = this.route.snapshot.params.id;
    this.rentalService.getById(this.id)
      .subscribe(data => {
        console.log(data);
        this.rental = data;
      }, error => console.log(error));

    this.form = this.formBuilder.group({
      name: [''],
      location: [''],
      description: [''],
      capacity: [''],
      minPrice: [''],
      numOfBeds: [''],
      numOfBedrooms: [''],
      numOfBathrooms: [''],
      squareMeters: [''],
      costPerPerson: ['']
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
    this.updateRental();
  }

  private updateRental() {
    this.rentalService.update(this.id, this.form.value)
      .pipe(first())
      .subscribe(
        data => {
          this.router.navigate([this.returnUrl]);
        },
        error => {
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

  onUpload() {
    console.log(this.selectedFile);

    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', this.selectedFile, this.selectedFile.name);

    this.rentalService.uploadMainPic(uploadImageData, this.rental.name);
  }
}
