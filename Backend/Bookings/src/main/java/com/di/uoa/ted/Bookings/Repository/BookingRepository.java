package com.di.uoa.ted.Bookings.Repository;

import com.di.uoa.ted.Bookings.Model.Booking;
import com.di.uoa.ted.Bookings.Model.RentHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByReservationId(Long reservationId);
    List<Booking> findBookingByRentHouse(Optional<RentHouse> rentHouseId);
}
