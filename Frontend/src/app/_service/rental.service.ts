import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import { map } from 'rxjs/operators';

import {Rentals} from '@app/_models/rentals';
import {environment} from '@environments/environment';
import {User} from '@app/_models/user';
import {Booking} from '@app/_models/booking';

@Injectable({ providedIn: 'root' })
export class RentalService {

  private rentalSubject: BehaviorSubject<Rentals[]>;
  public rental: Observable<Rentals[]>;

  private OnBookingRentalSubject: BehaviorSubject<Rentals>;
  public OnBookingRental: Observable<Rentals>;

  private bookingSubject: BehaviorSubject<Booking>;
  public userBooking: Observable<Booking>;

  constructor(
    private router: Router,
    private http: HttpClient
  ) {
    this.rentalSubject = new BehaviorSubject<Rentals[]>(JSON.parse(sessionStorage.getItem('rentals')));
    this.rental = this.rentalSubject.asObservable();

    this.OnBookingRentalSubject = new BehaviorSubject<Rentals>(JSON.parse(sessionStorage.getItem('OnBookingRental')));
    this.OnBookingRental = this.OnBookingRentalSubject.asObservable();

    this.bookingSubject = new BehaviorSubject<Booking>(JSON.parse(sessionStorage.getItem('UserBooking')));
    this.userBooking = this.bookingSubject.asObservable();
  }

  public get rentalValue(): Rentals[] {
    return this.rentalSubject.value;
  }

  public get OnBookingRentalValue(): Rentals {
    return this.OnBookingRentalSubject.value;
  }

  public get userBookingValue(): Booking {
    return this.bookingSubject.value;
  }


  register(rental: Rentals, userId: string) {
    return this.http.post(`${environment.apiUrl}/rentals/rentHouse/` + userId + `/register`, rental);
  }

  book(booking: Booking, rentHouseId: string, userId: string) {
    return this.http.post(`${environment.apiUrl}/book/` + rentHouseId + '/' + userId + `/register`, booking);
  }

  getBookingDetails(booking: Booking, rentHouseId: string) {
    return this.http.post<any>(`${environment.apiUrl}/book/getBookingDetails/${rentHouseId}`, booking)
      .pipe(map(details => {
        return details;
      }));
  }

  getFilterRents(params) {
    // console.log('params from GetFilterRents ' + JSON.stringify(params));
    return this.http.post<Rentals[]>(`${environment.apiUrl}/book/searchProperty`, params)
      .pipe(map(rentalData => {
        // store user details and jwt token in local storage to keep user logged in between page refreshes
        sessionStorage.setItem('rentals', JSON.stringify(rentalData));
        sessionStorage.setItem('UserBooking', JSON.stringify(params));
        this.rentalSubject.next(rentalData);
        this.bookingSubject.next(params);
        return rentalData;
      }));
  }

  getAll() {
    return this.http.get<Rentals[]>(`${environment.apiUrl}/rentals/search`);
  }

  getRentalsByOwner(id: string){
    return this.http.get<Rentals[]>(`${environment.apiUrl}/rentals/${id}`);
  }

  getById(id: string) {
    return this.http.get<Rentals>(`${environment.apiUrl}/rentals/profile/${id}`)
      .pipe(map(rental => {
        sessionStorage.setItem('OnBookingRental', JSON.stringify(rental));
        this.OnBookingRentalSubject.next(rental);
        return rental;
      }));
  }

  getByReservation(bookingId: string) {
    return this.http.get<Rentals>(`${environment.apiUrl}/rentals/reservationRental/${bookingId}`)
      .pipe(map(rental => {
        return rental;
      }));
  }

  getOwnerByRentalId(id: string) {
    return this.http.get<User>(`${environment.apiUrl}/rentals/profile/owner/${id}`);
  }

  update(id, params) {
    return this.http.put(`${environment.apiUrl}/rentals/update/${id}`, params)
      .pipe(map(x => {
        return x;
      }));
  }

  deleteRental(id: string) {
    return this.http.delete(`${environment.apiUrl}/rentals/delete/${id}`)
      .pipe(map(x => {
        return x;
      }));
  }

  cancelReservation(id: string) {
    return this.http.delete(`${environment.apiUrl}/book/cancel/${id}`)
      .pipe(map(x => {
        return x;
      }));
  }

  uploadMainPic(uploadImageData: FormData, name: string){
    return this.http.post<any>(`${environment.imageUrl}/uploadMainRentalPic/${name}`, uploadImageData)
      .subscribe((response) => {
        // console.log('Upload Response' + JSON.stringify(response));
      });
  }

  uploadRentalInterior(uploadImagesData: FormData, name: string) {
    return this.http.post<any>(`${environment.imageUrl}/uploadRentalPic/${name}`, uploadImagesData)
      .subscribe((response) => {
        // console.log('Upload Response Interior Pics' + JSON.stringify(response));
      });
  }

  deleteMainRentalPic(id: string) {
    return this.http.delete(`${environment.imageUrl}/mainRentalPic/delete/${id}`)
      .pipe(map(x => {
        return x;
      }));
  }

  downloadMainPic(id: string){
    return this.http.get<any>(`${environment.imageUrl}/getMainRentalPic/${id}`);
  }

  downloadInteriorPic(rentalId: string){
    return this.http.get<any>(`${environment.imageUrl}/getRentalInteriorPic/${rentalId}`);
  }


  addComment(comment: string, bookingId: string, userId: string) {
    return this.http.post(`${environment.apiUrl}/rentals/comments/` + bookingId + '/' + userId + `/add`, comment);
  }

  getComments(id: string) {
    return this.http.get<any>(`${environment.apiUrl}/rentals/comments/${id}`);
  }

  getRecommendations(userId: string) {
    return this.http.get<Rentals[]>(`${environment.apiUrl}/rentals/getRecommendations/${userId}`);
  }
}
