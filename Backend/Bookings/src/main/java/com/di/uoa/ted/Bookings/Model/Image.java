package com.di.uoa.ted.Bookings.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    private String name;
    private String type;
    
    @Lob
    private byte[] data;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private RentHouse rentHouse;
    @ManyToOne
    @JsonIgnore
    private RentHouse rentHouseInteriorImages;

    public Image() {
    }

    public Image(Long imageId, String name, String type, byte[] data) {
        this.imageId = imageId;
        this.name = name;
        this.type = type;
        this.data = data;
    }

    public Long getImageId() {
        return this.imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() { return type; }

    public void setType(String path) {
        this.type = path;
    }

    public byte[] getPicByte() {
        return data;
    }

    public void setPicByte(byte[] picByte) {
        this.data = picByte;
    }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public RentHouse getRentHouse() { return rentHouse; }

    public void setRentHouse(RentHouse rentHouse) { this.rentHouse = rentHouse; }

    public RentHouse getRentHouseInteriorImages() {
        return rentHouseInteriorImages;
    }

    public void setRentHouseInteriorImages(RentHouse rentHouseInteriorImages) {
        this.rentHouseInteriorImages = rentHouseInteriorImages;
    }
}
