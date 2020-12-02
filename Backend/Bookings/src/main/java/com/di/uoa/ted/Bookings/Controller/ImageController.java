package com.di.uoa.ted.Bookings.Controller;

import com.di.uoa.ted.Bookings.Model.Image;
import com.di.uoa.ted.Bookings.Repository.ImageRepository;
import com.di.uoa.ted.Bookings.Repository.RentHouseRepository;
import com.di.uoa.ted.Bookings.Repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("bookings/images")
public class ImageController {

    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final RentHouseRepository rentHouseRepository;

    ImageController(ImageRepository imageRepository,
                    UserRepository userRepository,
                    RentHouseRepository rentHouseRepository) {
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        this.rentHouseRepository = rentHouseRepository;
    }

    @PostMapping("/uploadProfilePic/{userId}")
    public Image uploadProfilePic(@RequestParam("imageFile") MultipartFile file, @PathVariable("userId") Long userId) throws UserNotFoundException, IOException {

        Image img = new Image();
        img.setName(file.getOriginalFilename());
        img.setType(file.getContentType());
        img.setPicByte(file.getBytes());

        this.deleteProfilePic(userId);

        return userRepository.findById(userId)
                .map(user -> {
                    img.setUser(user);
                    return imageRepository.save(img);
                }).orElseThrow(() -> new UserNotFoundException("Image Not Found"));
    }

    @PostMapping("/uploadMainRentalPic/{rentalName}")
    public Image uploadMainRentalPic(@RequestParam("imageFile") MultipartFile file, @PathVariable("rentalName") String rentalName) throws IOException, UserNotFoundException {
        Image img = new Image();
        img.setName(file.getOriginalFilename());
        img.setType(file.getContentType());
        img.setPicByte(file.getBytes());


        return rentHouseRepository.findByName(rentalName)
                .map(rental -> {
                    this.deleteMainRentalPic(rental.getRentHouse_id());
                    img.setRentHouse(rental);
                    rental.setRentHouseProfilePic(img);
                    return imageRepository.save(img);
                }).orElseThrow(() -> new UserNotFoundException("Rental Not Found"));
    }

    @PostMapping("/uploadRentalPic/{rentalName}")
    public void uploadRentalPic(@RequestParam("imageFile") MultipartFile[] files, @PathVariable("rentalName") String rentalName) throws IOException, UserNotFoundException {

        Arrays.stream(files).forEach(file -> {
            try {
                Image img = new Image();
                img.setName(file.getOriginalFilename());
                img.setType(file.getContentType());
                img.setPicByte(file.getBytes());
                rentHouseRepository.findByName(rentalName)
                        .map(rental -> {
                            img.setRentHouseInteriorImages(rental);
                            img.setRentHouse(rental);
                            return imageRepository.save(img);
                        }).orElseThrow(() -> new UserNotFoundException("Image Not Found"));
            } catch (IOException | UserNotFoundException e) {

            }
        });
    }

    @GetMapping("/getRentalInteriorPic/{rentHouseId}" )
    public List<Image> getInteriorRentalPic(@PathVariable("rentHouseId") Long rentHouseId) {
        System.out.println("getInterior... ");
        return imageRepository.findAllByRentHouseRentalId(rentHouseId);
    }

    @GetMapping(path = { "/getProfilePic/{userId}" })
    public Image getProfilePic(@PathVariable("userId") Long userId) {
        Optional<Image> retrievedImage = imageRepository.findByUserId(userId);
        if (retrievedImage.isPresent()) {
            Image img = new Image();
            img.setName(retrievedImage.get().getName());
            img.setType(retrievedImage.get().getType());
            img.setPicByte((retrievedImage.get().getPicByte()));
            return img;
        }
        return null;
    }

    @GetMapping(path = { "/getMainRentalPic/{rentHouseId}" })
    public Image getMainRentalPic(@PathVariable("rentHouseId") Long rentHouseId) throws IOException {
        Optional<Image> retrievedImage = imageRepository.findByRentHouseRentalId(rentHouseId);
        if (retrievedImage.isPresent()) {
            Image img = new Image();
            img.setName(retrievedImage.get().getName());
            img.setType(retrievedImage.get().getType());
            img.setPicByte(retrievedImage.get().getPicByte());
            return img;
        }
        return null;
    }

    @DeleteMapping("/profilePic/delete/{userId}")
    void deleteProfilePic(@PathVariable(value = "userId") Long userId){
        Optional<Image> retrievedImage = imageRepository.findByUserId(userId);
        if (retrievedImage.isPresent()) {
            imageRepository.deleteById(retrievedImage.get().getImageId());
        }
    }

    @DeleteMapping("/mainRentalPic/delete/{rentalId}")
    void deleteMainRentalPic(@PathVariable(value = "rentalId") Long rentalId){
        Optional<Image> retrievedImage = imageRepository.findByRentHouseRentalId(rentalId);
        if (retrievedImage.isPresent()) {
            imageRepository.deleteById(retrievedImage.get().getImageId());
        }
    }
}

