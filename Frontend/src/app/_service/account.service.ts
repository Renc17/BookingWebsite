import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import {BehaviorSubject, Observable, of} from 'rxjs';
import {map} from 'rxjs/operators';

import { environment } from '../../environments/environment';
import { User } from '@app/_models/user';
import {FormBuilder, FormGroup} from '@angular/forms';


@Injectable({ providedIn: 'root' })
export class AccountService {
  private userSubject: BehaviorSubject<User>;
  public user: Observable<User>;

  newDataUser: FormGroup;

  constructor(
    private router: Router,
    private http: HttpClient,
    private formBuilder: FormBuilder
  ) {
    this.userSubject = new BehaviorSubject<User>(JSON.parse(sessionStorage.getItem('user')));
    this.user = this.userSubject.asObservable();
  }

  public get userValue(): User {
    return this.userSubject.value;
  }

  login(user: { username: string, password: string } ): Observable<any>{
    return this.http.post<any>(`${environment.apiUrl}/login`, user)
      .pipe(map(userData => {
        // store user details and jwt token in local storage to keep user logged in between page refreshes
        sessionStorage.setItem('user', JSON.stringify(userData.user));
        const token = 'Bearer ' + userData.token;
        sessionStorage.setItem('token', token);
        console.log('login token : ' + token);
        this.userSubject.next(userData.user);
        return user;
      }));
  }

  logout() {
    // remove user from local storage and set current user to null
    sessionStorage.removeItem('user');
    sessionStorage.removeItem('token');
    this.userSubject.next(null);
    this.router.navigate(['/account/login']);
  }

  register(user: User) {
     return this.http.post(`${environment.apiUrl}/users/register`, user);
  }

  getAll() {
    return this.http.get<User[]>(`${environment.apiUrl}/admin`);
  }

  getById(id: string) {
    return this.http.get<User>(`${environment.apiUrl}/users/${id}`);
  }

  update(id, params) {
    this.newDataUser = this.formBuilder.group({
      username: [''],
      password: ['']
    });
    return this.http.put(`${environment.apiUrl}/users/${id}`, params)
      .pipe(map(x => {
        // update stored user if the logged in user updated their own record
        if (id === this.userValue.id) {
          // update local storage
          this.newDataUser.value.username = params.username;
          if (params.password !== null){
            this.newDataUser.value.password = params.password;
          }else {
            this.newDataUser.value.password = this.userValue.password;
          }

          this.login(this.newDataUser.value).toPromise();
        }
        return x;
      }));
  }

  getMyReservations(id: string){
    return this.http.get<any>(`${environment.apiUrl}/book/getReservations/${id}`);
  }

  delete(id: string) {
    return this.http.delete(`${environment.apiUrl}/users/${id}`)
      .pipe(map(x => {
        // auto logout if the logged in user deleted their own record
        if (id === this.userValue.id) {
          this.logout();
        }
        return x;
      }));
  }

  upload(uploadImageData: FormData, id: string){
    return this.http.post<any>(`${environment.imageUrl}/uploadProfilePic/${id}`, uploadImageData)
      .subscribe((response) => {
        // console.log('Upload Response' + JSON.stringify(response));
      });
  }

  download(id: string): Observable<any>{
    console.log('downloading...');
    return this.http.get<any>(`${environment.imageUrl}/getProfilePic/${id}`)
      .pipe(map(res => {
        return res;
      }));
  }

  deleteProfilePic(id: string) {
    return this.http.delete(`${environment.imageUrl}/profilePic/delete/${id}`)
      .pipe(map(x => {
        // console.log('map delete user : ' + id);
        return x;
      }));
  }

  isLoggedIn(){
    const user = sessionStorage.getItem('user');
    return !(user === null);
  }

}

