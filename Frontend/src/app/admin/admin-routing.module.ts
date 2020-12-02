import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {AdminLayoutComponent} from '@app/admin/adminLayout/AdminLayout.component';
import {AdminprofileComponent} from '@app/admin/adminprofile/adminprofile.component';
import {EditComponent} from '@app/admin/edit/edit.component';
import {GetRentalsByOwnerComponent} from '@app/admin/get-rentals-by-owner/get-rentals-by-owner.component';


const routes: Routes = [
  {
    path: '', component: AdminLayoutComponent,
    children: [
      { path: 'profile/:id', component: AdminprofileComponent },
      { path: 'edit/:id', component: EditComponent },
      { path: 'rentalByOwner/:id', component: GetRentalsByOwnerComponent }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
