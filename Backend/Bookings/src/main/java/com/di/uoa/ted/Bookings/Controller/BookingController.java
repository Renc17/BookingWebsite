package com.di.uoa.ted.Bookings.Controller;

import com.di.uoa.ted.Bookings.Model.Booking;
import com.di.uoa.ted.Bookings.Model.RentHouse;
import com.di.uoa.ted.Bookings.Model.SearchProperty;
import com.di.uoa.ted.Bookings.Repository.BookingRepository;
import com.di.uoa.ted.Bookings.Repository.RentHouseRepository;
import com.di.uoa.ted.Bookings.Repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("bookings/book/")
public class BookingController {

    private final BookingRepository bookingRepository;
    private final RentHouseRepository rentHouseRepository;
    private final UserRepository userRepository;

    public BookingController(BookingRepository bookingRepository,
                             RentHouseRepository rentHouseRepository,
                             UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.rentHouseRepository = rentHouseRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("all")
    public List<Booking> getAll(){
        return bookingRepository.findAll();
    }

    @PostMapping("searchProperty")
    public List<RentHouse> searchProperty(@RequestBody SearchProperty searchProperty){

        return rentHouseRepository.findAllByLocationOrderByMinPriceAsc(searchProperty.getLocation())
                .stream()
                .filter(rentHouse -> rentHouse.isAvailable(rentHouse.getBookingList(), searchProperty.getCheckin(), searchProperty.getCheckout())
                        && searchProperty.getGuests() <= rentHouse.getCapacity() && searchProperty.getGuests() >= rentHouse.getMinNumGuests())
                .collect(Collectors.toList());
    }

    @GetMapping("getReservations/{userId}")
    public List<Booking> getReservationsByOwner(@PathVariable(value = "userId") Long owner) {
        return bookingRepository.findByReservationId(owner);
    }

    @GetMapping("getBooking/{rentHouseId}")
    public List<Booking> getBookingByRental(@PathVariable(value = "rentHouseId") Long rentHouseId)
            throws UserNotFoundException {
        Optional<RentHouse> rentHouse = rentHouseRepository.findById(rentHouseId);
        return bookingRepository.findBookingByRentHouse(rentHouse);
    }

    @PostMapping("{rentHouseId}/{userId}/register")
    public Booking makeABooking(@PathVariable(value = "rentHouseId") Long rentHouseId, @PathVariable(value = "userId") Long userId, @RequestBody SearchProperty searchProperty) throws UserNotFoundException {

        Booking booking = new Booking();
        return rentHouseRepository.findById(rentHouseId).map(rentHouse -> {
            long costByNumGuests = searchProperty.getGuests()*rentHouse.getCostPerPerson();
            long stayingDays = (searchProperty.getCheckout().getTime() - searchProperty.getCheckin().getTime())/ (1000 * 3600 * 24);
            long price;

            if (searchProperty.getGuests() <= rentHouse.getMinNumGuests()){
                price = rentHouse.getMinPrice();
            }else {
                price = stayingDays*costByNumGuests;
            }
            booking.setPrice(price);
            booking.setRentHouse(rentHouse);
            booking.setGuests(searchProperty.getGuests());
            booking.setCheckin(searchProperty.getCheckin());
            booking.setCheckout(searchProperty.getCheckout());
            booking.setStayingDuration(stayingDays);

            userRepository.findById(userId).map(user -> {
                booking.setReservation(user);
                return booking;
            });

            System.out.println("Saving booking");
            return bookingRepository.save(booking);
        }).orElseThrow(() -> new UserNotFoundException("Rental Not Found"));
    }

    @PostMapping("getBookingDetails/{rentalId}")
    public Map<Object, Object> getBookingDetails(@PathVariable(value = "rentalId")Long rentalId, @RequestBody SearchProperty searchProperty){

        Map<Object, Object> details = new HashMap<>();
        rentHouseRepository.findById(rentalId).map(rentHouse -> {
            long costByNumGuests = searchProperty.getGuests()*rentHouse.getCostPerPerson();
            long stayingDays = (searchProperty.getCheckout().getTime() - searchProperty.getCheckin().getTime())/ (1000 * 3600 * 24);
            long price;

            if (searchProperty.getGuests() <= rentHouse.getMinNumGuests()){
                price = rentHouse.getMinPrice();
            }else {
                price = stayingDays*costByNumGuests;
            }
            details.put("price", price);
            details.put("costByNumGuests", costByNumGuests);
            details.put("duration", stayingDays);
            return null;
        });
        return details;
    }

    @DeleteMapping("cancel/{id}")
    public void deleteBooking(@PathVariable(value = "id")Long id){
        bookingRepository.deleteById(id);
    }

}

