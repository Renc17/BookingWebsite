import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import {AdminLayoutComponent} from '@app/admin/adminLayout/AdminLayout.component';
import {AdminprofileComponent} from '@app/admin/adminprofile/adminprofile.component';
import {EditComponent} from '@app/admin/edit/edit.component';
import { GetRentalsByOwnerComponent } from './get-rentals-by-owner/get-rentals-by-owner.component';
import {RentalsModule} from '@app/rentals/rentals.module';


@NgModule({
    imports: [
        CommonModule,
        ReactiveFormsModule,
        AdminRoutingModule,
        RentalsModule
    ],
  declarations: [
    AdminLayoutComponent,
    AdminprofileComponent,
    EditComponent,
    GetRentalsByOwnerComponent
  ]
})
export class AdminModule { }
