package com.di.uoa.ted.Bookings.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String repassword;
    private String email;
    private String cel;
    private String name;
    private String surname;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "userProfilePic")
    private Image userProfilePic;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private final Set<Role> roles = new HashSet<>();


    @OneToMany(targetEntity=RentHouse.class, mappedBy="owner", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private final List<RentHouse> rentHouse = new ArrayList<>();

    @OneToMany(mappedBy="user", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private final List<RentHouseComments> rentHouseComments = new ArrayList<>();

    @OneToMany(mappedBy = "reservation", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> reservations = new ArrayList<>();


    public User() {
    }

    public User(Long id, String username,
                String password, String repassword, String email,
                String cel, String name, String surname, Image userProfilePic) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.cel = cel;
        this.name = name;
        this.surname = surname;
        this.repassword = repassword;
        this.userProfilePic = userProfilePic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCel() {
        return cel;
    }

    public void setCel(String cel) {
        this.cel = cel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public List<RentHouse> getRentHouse() { return rentHouse; }

    public void setRoles(Role roles){
        this.roles.add(roles);
        System.out.println(this);
        roles.getUsers().add(this);
    }

    public List<Booking> getReservations() { return reservations; }

    public void setReservations(List<Booking> reservations) { this.reservations = reservations; }

    public Image getUserProfilePic() { return userProfilePic; }

    public void setUserProfilePic(Image userProfilePic) { this.userProfilePic = userProfilePic; }
}
