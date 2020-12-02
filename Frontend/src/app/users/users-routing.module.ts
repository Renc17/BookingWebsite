import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LayoutComponent } from './layout/layout.component';
import { EditComponent } from './edit/edit.component';
import { ProfileComponent } from '@app/users/profile/profile.component';

const routes: Routes = [
  {
    path: '', component: LayoutComponent,
    children: [
      { path: 'profile/:id', component: ProfileComponent },
      { path: 'edit/:id', component: EditComponent },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsersRoutingModule { }
