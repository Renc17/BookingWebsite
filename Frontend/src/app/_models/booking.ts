import {Rentals} from '@app/_models/rentals';

export class Booking{
  id: string;
  checkin: Date;
  checkout: Date;
  guests: number;
  price: number;
  stayingDuration: number;
  rentHouse: Rentals;
}
