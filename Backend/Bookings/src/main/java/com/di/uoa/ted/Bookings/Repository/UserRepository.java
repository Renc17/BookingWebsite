package com.di.uoa.ted.Bookings.Repository;

import com.di.uoa.ted.Bookings.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4200")
@Repository("Users")
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
