package com.di.uoa.ted.Bookings.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class RentHouseComments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String Comment;

    @ManyToOne
    @JsonIgnore
    private RentHouse rentHouse;

    @ManyToOne
    private User user;

    public RentHouseComments() {
    }

    public RentHouseComments(Long id,
                             String comment,
                             RentHouse rentHouse,
                             User user) {
        this.id = id;
        Comment = comment;
        this.rentHouse = rentHouse;
        this.user = user;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public RentHouse getRentHouse() {
        return rentHouse;
    }

    public void setRentHouse(RentHouse rentHouse) {
        this.rentHouse = rentHouse;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
