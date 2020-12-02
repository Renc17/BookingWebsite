package com.di.uoa.ted.Bookings.Model;
import java.util.Date;

public class SearchProperty {

    private Date checkin;
    private Date checkout;
    private int guests;
    private String location;

    public SearchProperty() {
    }

    public SearchProperty(Date check_in, Date check_out, int guests, String location) {
        this.checkin = check_in;
        this.checkout = check_out;
        this.guests = guests;
        this.location = location;
    }

    public Date getCheckin() {
        return checkin;
    }

    public void setCheckin(Date check_in) {
        this.checkin = check_in;
    }

    public Date getCheckout() {
        return checkout;
    }

    public void setCheckout(Date check_out) {
        this.checkout = check_out;
    }

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
