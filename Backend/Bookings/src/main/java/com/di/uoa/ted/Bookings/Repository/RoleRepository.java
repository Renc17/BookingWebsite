package com.di.uoa.ted.Bookings.Repository;

import com.di.uoa.ted.Bookings.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String roleName);
}
