package com.di.uoa.ted.Bookings.Repository;

import com.di.uoa.ted.Bookings.Model.RentHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@Repository("RentHouse")
public interface RentHouseRepository extends JpaRepository<RentHouse, Long> {

    List<RentHouse> findByOwnerId(Long ownerId);
    List<RentHouse> findAllByLocationOrderByMinPriceAsc(String location);
    Optional<RentHouse> findByName(String name);
}
