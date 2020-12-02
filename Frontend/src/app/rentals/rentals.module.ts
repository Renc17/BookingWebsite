import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { RentalsRoutingModule } from '@app/rentals/rentals-routing.module';
import { RentalLayoutComponent } from '@app/rentals/rental-layout/rental-layout.component';
import { AddRentalsComponent } from './add-rentals/add-rentals.component';
import { RentalSearchComponent } from './rentalSearch/rentalSearch.component';
import { RentalProfileComponent } from './rental-profile/rental-profile.component';
import { UpdateRentalComponent } from './update-rental/update-rental.component';
import { GetMainRentalPicPipe } from './rentalSearch/get-main-rental-pic.pipe';

@NgModule({
    imports: [
        CommonModule,
        ReactiveFormsModule,
        RentalsRoutingModule
    ],
    exports: [
        GetMainRentalPicPipe
    ],
    declarations: [
        RentalLayoutComponent,
        AddRentalsComponent,
        RentalSearchComponent,
        RentalProfileComponent,
        UpdateRentalComponent,
        GetMainRentalPicPipe,
    ]
})
export class RentalsModule { }
