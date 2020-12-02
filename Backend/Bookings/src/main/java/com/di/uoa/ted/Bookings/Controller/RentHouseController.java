package com.di.uoa.ted.Bookings.Controller;

import com.di.uoa.ted.Bookings.Model.Booking;
import com.di.uoa.ted.Bookings.Model.RentHouse;
import com.di.uoa.ted.Bookings.Model.User;
import com.di.uoa.ted.Bookings.Repository.BookingRepository;
import com.di.uoa.ted.Bookings.Repository.RentHouseRepository;
import com.di.uoa.ted.Bookings.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("bookings/rentals")
public class RentHouseController {

    private final RentHouseRepository rentHouseRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    /*@Autowired
    private Training training;*/

    public RentHouseController(@Qualifier("RentHouse") RentHouseRepository rentHouseRepository,
                               UserRepository userRepository,
                               BookingRepository bookingRepository) {
        this.rentHouseRepository = rentHouseRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

    @GetMapping("/search")
    public List<RentHouse> getAllRentHouses(){
        return rentHouseRepository.findAll();
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<RentHouse> getRentById(@PathVariable(value = "id") Long houseId)
            throws UserNotFoundException {
        RentHouse rentHouse = rentHouseRepository.findById(houseId)
                .orElseThrow( () -> new UserNotFoundException( "Rent does not exist in database"));
        return ResponseEntity.ok().body(rentHouse);
    }

    @GetMapping("/profile/owner/{id}")
    public User getOwnerByRentId(@PathVariable(value = "id") Long houseId)
            throws UserNotFoundException {
        RentHouse rentHouse = rentHouseRepository.findById(houseId)
                .orElseThrow( () -> new UserNotFoundException( "Rental does not exist in database"));
        return rentHouse.getOwner();
    }

    @GetMapping("/{owner}")
    public List<RentHouse> getRentByOwner(@PathVariable(value = "owner") Long owner)
            throws UserNotFoundException {
        return rentHouseRepository.findByOwnerId(owner);
    }

    @PostMapping("/rentHouse/{userId}/register")
    public RentHouse addRentHouse(@PathVariable(value = "userId") Long userId, @RequestBody RentHouse rentHouse) throws UserNotFoundException {
        System.out.println("Adding new Rental " + rentHouse.getName());
        return userRepository.findById(userId).map(user -> {
            rentHouse.setOwner(user);
            return rentHouseRepository.save(rentHouse);
        }).orElseThrow(() -> new UserNotFoundException("User Not Found"));
    }

    @GetMapping("/reservationRental/{bookingId}")
    public Optional<RentHouse> getRentalByReservation(@PathVariable(value = "bookingId")Long bookingId){
        return bookingRepository.findById(bookingId)
                .map(Booking::getRentHouse);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<RentHouse> updateRentHouse(@PathVariable( value = "id") Long houseId, @RequestBody RentHouse houseUpdate)
            throws UserNotFoundException{

        RentHouse rentHouse = rentHouseRepository.findById(houseId)
                .orElseThrow(() -> new UserNotFoundException("Rent House not found"));

        if (!houseUpdate.getName().isEmpty()){
            rentHouse.setName(houseUpdate.getName());
        }
        if (!houseUpdate.getName().isEmpty()) {
            rentHouse.setCapacity(houseUpdate.getCapacity());
        }
        if (!(houseUpdate.getCostPerPerson() == 0)) {
            rentHouse.setCostPerPerson(houseUpdate.getCostPerPerson());
        }
        if (!houseUpdate.getDescription().isEmpty()) {
            rentHouse.setDescription(houseUpdate.getDescription());
        }
        if (!(houseUpdate.getMinPrice() == 0)) {
            rentHouse.setMinPrice(houseUpdate.getMinPrice());
        }
        if (!(houseUpdate.getNumOfBathrooms() == 0)) {
            rentHouse.setNumOfBathrooms(houseUpdate.getNumOfBathrooms());
        }
        if (!(houseUpdate.getNumOfBedrooms() == 0)) {
            rentHouse.setNumOfBedrooms(houseUpdate.getNumOfBedrooms());
        }
        if (!(houseUpdate.getNumOfBeds() == 0)) {
            rentHouse.setNumOfBeds(houseUpdate.getNumOfBeds());
        }
        if (!(houseUpdate.getSquareMeters() == 0)) {
            rentHouse.setSquareMeters(houseUpdate.getSquareMeters());
        }
        if (!houseUpdate.getLocation().isEmpty()) {
            rentHouse.setLocation(houseUpdate.getLocation());
        }

        final RentHouse updatedHouse = rentHouseRepository.save(rentHouse);
        return ResponseEntity.ok(updatedHouse);
    }

    @DeleteMapping("/delete/{id}")
    void deleteRentHouse(@PathVariable( value = "id") Long id) {
        rentHouseRepository.deleteById(id);
    }




    /*@GetMapping("/getRecommendations/{userId}")
    public List<Optional<RentHouse>> getRecommendations(@PathVariable(value = "userId")int userId){

        List<recommendation> recommendations = training.getRecommendations(userId);
        List<Optional<RentHouse>> recommendedRentHouse = new ArrayList<>();
        int topK = 3;
        int i = 0;
        for (com.di.uoa.ted.Bookings.Bonus.recommendation recommendation : recommendations) {
            Optional<RentHouse> rentHouse = rentHouseRepository.findById((long) recommendation.getListing());
            recommendedRentHouse.add(rentHouse);
            i++;
            if (i == topK){
                break;
            }
        }
        return recommendedRentHouse;
    }*/
}
