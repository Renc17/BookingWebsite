package com.di.uoa.ted.Bookings.Repository;

import com.di.uoa.ted.Bookings.Model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByName(String name);
    Optional<Image> findByUserId(Long userId);
    void deleteByUserId(Long userId);
    Optional<Image> findByRentHouseRentalId(Long rentHouseId);
    List<Image> findAllByRentHouseRentalId(Long rentHouseId);
}
