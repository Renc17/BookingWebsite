import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { UsersRoutingModule } from './users-routing.module';
import { LayoutComponent } from './layout/layout.component';
import { ProfileComponent } from '@app/users/profile/profile.component';
import { EditComponent } from './edit/edit.component';
import { RentalsModule } from '@app/rentals/rentals.module';

@NgModule({
    imports: [
        CommonModule,
        ReactiveFormsModule,
        UsersRoutingModule,
        RentalsModule
    ],
  declarations: [
    LayoutComponent,
    ProfileComponent,
    EditComponent
  ]
})
export class UsersModule { }
