package com.di.uoa.ted.Bookings.Controller;

import com.di.uoa.ted.Bookings.Model.RentHouseComments;
import com.di.uoa.ted.Bookings.Repository.BookingRepository;
import com.di.uoa.ted.Bookings.Repository.RentHouseCommentsRepository;
import com.di.uoa.ted.Bookings.Repository.RentHouseRepository;
import com.di.uoa.ted.Bookings.Repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("bookings/rentals/comments")
public class RentHouseCommentsController {

    private final RentHouseCommentsRepository rentHouseCommentsRepository;
    private final RentHouseRepository rentHouseRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public RentHouseCommentsController(RentHouseCommentsRepository rentHouseCommentsRepository,
                                       RentHouseRepository rentHouseRepository,
                                       BookingRepository bookingRepository,
                                       UserRepository userRepository) {
        this.rentHouseCommentsRepository = rentHouseCommentsRepository;
        this.rentHouseRepository = rentHouseRepository;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/{rentalId}")
    public List<RentHouseComments> getCommentsByRental(@PathVariable(value = "rentalId") Long houseId)
            throws UserNotFoundException {
        return rentHouseCommentsRepository.findAllByRentHouseRentalId(houseId);
    }

    @PostMapping("/{bookingId}/{userId}/add")
    public RentHouseComments addCommentOnRental(@PathVariable(value = "bookingId") Long bookingId, @PathVariable(value = "userId") Long userId, @RequestBody String comment) throws UserNotFoundException {
        RentHouseComments rentHouseComments = new RentHouseComments();

        return bookingRepository.findById(bookingId)
                .map(booking -> {
                    Long rentalId = booking.getRentHouse().getRentHouse_id();

                    rentHouseRepository.findById(rentalId).map(rental -> {
                        rentHouseComments.setComment(comment);
                        rentHouseComments.setRentHouse(rental);
                        return rentHouseComments;

                    });

                    userRepository.findById(userId).map(user -> {
                        rentHouseComments.setUser(user);
                        return rentHouseComments;
                    });
                    return rentHouseCommentsRepository.save(rentHouseComments);
                }).orElseThrow(() -> new UserNotFoundException("Booking Not Found"));
    }
}
