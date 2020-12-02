package com.di.uoa.ted.Bookings.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private RentHouse rentHouse;

    @ManyToOne
    @JsonIgnore
    private User reservation;

    private Date checkin;
    private Date checkout;

    private long price;
    private int guests;
    private long stayingDuration;

    public Booking() {
    }

    public Booking(Long id, RentHouse rentHouse, Date beginDate, Date endDate, int price, int guests, int stayingDuration) {
        this.id = id;
        this.rentHouse = rentHouse;
        this.checkin = beginDate;
        this.checkout = endDate;
        this.price = price;
        this.guests = guests;
        this.stayingDuration = stayingDuration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RentHouse getRentHouse() {
        return rentHouse;
    }

    public void setRentHouse(RentHouse rentHouse) {
        this.rentHouse = rentHouse;
    }

    public Date getCheckin() {
        return checkin;
    }

    public void setCheckin(Date beginDate) {
        this.checkin = beginDate;
    }

    public Date getCheckout() {
        return checkout;
    }

    public void setCheckout(Date endDate) {
        this.checkout = endDate;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public User getReservation() {
        return reservation;
    }

    public void setReservation(User reservation) {
        this.reservation = reservation;
    }

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }

    public long getStayingDuration() {
        return stayingDuration;
    }

    public void setStayingDuration(long stayingDuration) {
        this.stayingDuration = stayingDuration;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", rentHouse=" + rentHouse.getName() +
                ", reservation=" + reservation.getUsername() +
                ", checkin=" + checkin +
                ", checkout=" + checkout +
                ", price=" + price +
                ", guests=" + guests +
                '}';
    }
}
