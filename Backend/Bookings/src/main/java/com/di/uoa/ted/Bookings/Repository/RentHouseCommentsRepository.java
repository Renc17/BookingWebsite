package com.di.uoa.ted.Bookings.Repository;

import com.di.uoa.ted.Bookings.Model.RentHouseComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentHouseCommentsRepository extends JpaRepository<RentHouseComments, Long> {

    List<RentHouseComments> findAllByRentHouseRentalId(Long id);
}
