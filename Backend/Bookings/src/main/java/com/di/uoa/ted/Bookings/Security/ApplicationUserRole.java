package com.di.uoa.ted.Bookings.Security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum ApplicationUserRole {
    ADMIN(Sets.newHashSet(ApplicationUserPermission.USERS_READ, ApplicationUserPermission.USERS_WRITE, ApplicationUserPermission.HOUSE_READ, ApplicationUserPermission.PROFILE_READ, ApplicationUserPermission.PROFILE_WRITE)),
    USER(Sets.newHashSet(ApplicationUserPermission.HOUSE_READ, ApplicationUserPermission.HOUSE_WRITE, ApplicationUserPermission.USERS_READ, ApplicationUserPermission.USERS_WRITE, ApplicationUserPermission.PROFILE_READ, ApplicationUserPermission.PROFILE_WRITE));

    private final Set<ApplicationUserPermission> permissions;


    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }


    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
