import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AuthGuard } from './_helpers/auth.guard';
import { HomeComponent } from '@app/home/home.component';
import { Role } from '@app/_models/role';

const accountModule = () => import('./account/account.module').then(x => x.AccountModule);
const usersModule = () => import('./users/users.module').then(x => x.UsersModule);
const adminModule = () => import('./admin/admin.module').then(x => x.AdminModule);
const rentalsModule = () => import('./rentals/rentals.module').then(x => x.RentalsModule);

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'users', loadChildren: usersModule , canActivate: [AuthGuard], canLoad: [AuthGuard], data: { role: [Role.user]} },
  { path: 'admin', loadChildren: adminModule , canLoad: [AuthGuard], canActivate: [AuthGuard], data: { role: [Role.admin]} },
  { path: 'account', loadChildren: accountModule },
  { path: 'rentals', loadChildren: rentalsModule },

  // otherwise redirect to home
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
