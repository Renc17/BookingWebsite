import { Component, OnInit } from '@angular/core';

import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';

import { AccountService} from '@app/_service/account.service';
import { AlertService } from '@app/_service/alert.service';

// Use CreateUser ( modified ) by users to add hotels in their profile

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {
  form: FormGroup;
  id: string;
  loading = false;
  submitted = false;
  returnUrl: string;
  user = null;

  selectedFile: File;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private accountService: AccountService,
    private alertService: AlertService
  ) {}

  ngOnInit() {
    this.id = this.route.snapshot.params.id;

    // password not required in edit mode
    const passwordValidators = [Validators.minLength(6)];

    this.form = this.formBuilder.group({
      name: [''],
      surname: [''],
      username: [''],
      password: ['', passwordValidators],
      repassword: ['', passwordValidators],
      email: [''],
      cel: [''],
    }, {
      validators: this.MustMatch('password', 'repassword')
    });

    this.returnUrl = '/admin/profile/' + this.id;
    this.user = this.accountService.getById(this.id)
      .subscribe(data => {
      console.log(data);
      console.log(this.id);
      this.user = data;
    }, error => console.log(error));
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
    this.updateUser();
  }

  private updateUser() {
    this.accountService.update(this.id, this.form.value)
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

  MustMatch(controlName: string, matchingControlName: string) {
    return (formGroup: FormGroup) => {
      const control = formGroup.controls[controlName];
      const matchingControl = formGroup.controls[matchingControlName];

      if (matchingControl.errors && !matchingControl.errors.mustMatch) {
        // return if another validator has already found an error on the matchingControl
        return;
      }

      // set error on matchingControl if validation fails
      if (control.value !== matchingControl.value) {
        matchingControl.setErrors({mustMatch: true});
      } else {
        matchingControl.setErrors(null);
      }
    };
  }

  public onFileChanged(event) {
    this.selectedFile = event.target.files[0];
  }

  onUpload() {
    console.log(this.selectedFile);

    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', this.selectedFile, this.selectedFile.name);

    this.accountService.upload(uploadImageData, this.accountService.userValue.id);
  }

  deleteProfilePic(){
    this.accountService.deleteProfilePic(this.accountService.userValue.id)
      .pipe(first())
      .subscribe(data => {
          console.log('data from delete : ' + data);
        },
        error => {
          this.alertService.error(error);
        });
  }
}
