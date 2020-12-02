import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RentalLayoutComponent } from '@app/rentals/rental-layout/rental-layout.component';
import { AddRentalsComponent } from '@app/rentals/add-rentals/add-rentals.component';
import {RentalSearchComponent} from '@app/rentals/rentalSearch/rentalSearch.component';
import {RentalProfileComponent} from '@app/rentals/rental-profile/rental-profile.component';
import {UpdateRentalComponent} from '@app/rentals/update-rental/update-rental.component';

const routes: Routes = [
  {
    path: '', component: RentalLayoutComponent,
    children: [
      { path: 'register', component: AddRentalsComponent },
      { path: 'getRentals', component: RentalSearchComponent },
      { path: 'profile/:id', component: RentalProfileComponent },
      { path: 'update/:id', component: UpdateRentalComponent }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RentalsRoutingModule { }
