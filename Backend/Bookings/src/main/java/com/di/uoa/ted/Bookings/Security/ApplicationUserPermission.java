package com.di.uoa.ted.Bookings.Security;

public enum  ApplicationUserPermission {
    USERS_READ("users:read"),
    USERS_WRITE("users:write"),
    HOUSE_READ("house:read"),
    HOUSE_WRITE("house:write"),
    PROFILE_READ("profile:read"),
    PROFILE_WRITE("profile:write");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
