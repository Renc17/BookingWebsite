package com.di.uoa.ted.Bookings.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class RentHouse{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rentalId;

    private String name;
    private int capacity;
    private int minNumGuests;
    private int minNumOfNights;
    private int costPerPerson;
    private int numOfBeds;
    private int numOfBedrooms;
    private int numOfBathrooms;
    private int squareMeters;
    private int minPrice;
    private String description;

    private String location;
    private String address;
    private String lon;
    private String lat;

    @ManyToOne
    @JsonIgnore
    private User owner;

    @OneToOne(mappedBy = "rentHouse", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Image rentHouseProfilePic;

    @OneToMany(mappedBy = "rentHouseInteriorImages", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Image> imageList = new ArrayList<>();

    @OneToMany(mappedBy = "rentHouse", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookingList = new ArrayList<>();

    @OneToMany(mappedBy="rentHouse", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RentHouseComments> rentHouseComments = new ArrayList<>();


    private boolean kitchen;
    private boolean freeParking;
    private boolean ac;
    private boolean fak;
    private boolean carbonAlarm;
    private boolean wifi;
    private boolean hairDryer;
    private boolean tv;
    private boolean iron;
    private boolean smokeAlarm;


    public RentHouse() {
    }

    public RentHouse(Long id, String name,
                     int capacity,
                     int minNumGuests, int minNumOfNights, int costPerPerson,
                     int numOfBeds,
                     int numOfBedrooms,
                     int numOfBathrooms,
                     int squareMeters,
                     int minPrice,
                     String description,
                     String location,
                     String address, String lon, String lat, boolean kitchen,
                     boolean freeParking,
                     boolean ac,
                     boolean fak,
                     boolean carbonAlarm,
                     boolean wifi,
                     boolean hairDryer,
                     boolean tv,
                     boolean iron,
                     boolean smokeAlarm) {
        this.rentalId = id;
        this.name = name;
        this.capacity = capacity;
        this.minNumGuests = minNumGuests;
        this.minNumOfNights = minNumOfNights;
        this.costPerPerson = costPerPerson;
        this.numOfBeds = numOfBeds;
        this.numOfBedrooms = numOfBedrooms;
        this.numOfBathrooms = numOfBathrooms;
        this.squareMeters = squareMeters;
        this.minPrice = minPrice;
        this.description = description;
        this.location = location;
        this.address = address;
        this.lon = lon;
        this.lat = lat;
        this.kitchen = kitchen;
        this.freeParking = freeParking;
        this.ac = ac;
        this.fak = fak;
        this.carbonAlarm = carbonAlarm;
        this.wifi = wifi;
        this.hairDryer = hairDryer;
        this.tv = tv;
        this.iron = iron;
        this.smokeAlarm = smokeAlarm;
    }

    public Long getRentHouse_id() {
        return rentalId;
    }

    public void setRentHouse_id(Long rentHouse_id) {
        this.rentalId = rentHouse_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getMinNumGuests() {
        return minNumGuests;
    }

    public void setMinNumGuests(int minNumGuests) {
        this.minNumGuests = minNumGuests;
    }

    public int getMinNumOfNights() { return minNumOfNights; }

    public void setMinNumOfNights(int minNumOfNights) {
        this.minNumOfNights = minNumOfNights;
    }

    public int getCostPerPerson() {
        return costPerPerson;
    }

    public void setCostPerPerson(int costPerPerson) {
        this.costPerPerson = costPerPerson;
    }

    public int getNumOfBeds() {
        return numOfBeds;
    }

    public void setNumOfBeds(int numOfBeds) {
        this.numOfBeds = numOfBeds;
    }

    public int getNumOfBedrooms() {
        return numOfBedrooms;
    }

    public void setNumOfBedrooms(int numOfBedrooms) {
        this.numOfBedrooms = numOfBedrooms;
    }

    public int getNumOfBathrooms() {
        return numOfBathrooms;
    }

    public void setNumOfBathrooms(int numOfBathrooms) {
        this.numOfBathrooms = numOfBathrooms;
    }

    public int getSquareMeters() {
        return squareMeters;
    }

    public void setSquareMeters(int squareMeters) {
        this.squareMeters = squareMeters;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public String getLon() { return lon; }

    public void setLon(String lon) { this.lon = lon; }

    public String getLat() { return lat; }

    public void setLat(String lat) { this.lat = lat; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public User getOwner() { return owner; }

    public void setOwner(User owner) { this.owner = owner; }

    public boolean isKitchen() {
        return kitchen;
    }

    public void setKitchen(boolean kitchen) {
        this.kitchen = kitchen;
    }

    public boolean isFreeParking() {
        return freeParking;
    }

    public void setFreeParking(boolean freeParking) {
        this.freeParking = freeParking;
    }

    public boolean isAc() {
        return ac;
    }

    public void setAc(boolean ac) {
        this.ac = ac;
    }

    public boolean isFak() {
        return fak;
    }

    public void setFak(boolean fak) {
        this.fak = fak;
    }

    public boolean isCarbonAlarm() {
        return carbonAlarm;
    }

    public void setCarbonAlarm(boolean carbonAlarm) {
        this.carbonAlarm = carbonAlarm;
    }

    public boolean isWifi() {
        return wifi;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public boolean isHairDryer() {
        return hairDryer;
    }

    public void setHairDryer(boolean hairDryer) {
        this.hairDryer = hairDryer;
    }

    public boolean isTv() {
        return tv;
    }

    public void setTv(boolean tv) {
        this.tv = tv;
    }

    public boolean isIron() {
        return iron;
    }

    public void setIron(boolean iron) {
        this.iron = iron;
    }

    public boolean isSmokeAlarm() {
        return smokeAlarm;
    }

    public void setSmokeAlarm(boolean smokeAlarm) {
        this.smokeAlarm = smokeAlarm;
    }

    public List<Booking> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    public List<RentHouseComments> getRentHouseComments() {
        return rentHouseComments;
    }

    public void setRentHouseComments(List<RentHouseComments> rentHouseComments) {
        this.rentHouseComments = rentHouseComments;
    }
    
    public Image getRentHouseProfilePic() { return rentHouseProfilePic; }

    public void setRentHouseProfilePic(Image rentHouseProfilePic) { this.rentHouseProfilePic = rentHouseProfilePic; }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    public boolean isAvailable(List<Booking> bookings, Date beginDate, Date endDate){

        boolean available = true;
        for (Booking booking: bookings){
            System.out.println(booking.getCheckin() + " - " + booking.getCheckout());
            available =! this.areOverLapping(booking.getCheckin(),booking.getCheckout(),beginDate,endDate);
            System.out.println("isAvailable " + available + " " + booking.getRentHouse().getName());
        }
        return available;
    }

    private boolean areOverLapping(Date b1, Date e1, Date b2, Date e2){
        return b1.before(e2) && e1.after(b2);
    }


    @Override
    public String toString() {
        return "RentHouse{" +
                "rentalId=" + rentalId +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
