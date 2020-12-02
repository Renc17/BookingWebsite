package com.di.uoa.ted.Bookings.Model;

public class AuthBody {
    String username;
    String password;

    public AuthBody() {
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
}
