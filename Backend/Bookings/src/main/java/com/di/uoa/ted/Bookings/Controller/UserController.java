package com.di.uoa.ted.Bookings.Controller;

import com.di.uoa.ted.Bookings.Model.User;
import com.di.uoa.ted.Bookings.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("bookings")
public class UserController {

    private final UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    UserController(@Qualifier("Users") UserRepository repository) {
        this.userRepository = repository;
    }

    @GetMapping("/admin")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId)
            throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow( () -> new UserNotFoundException( "Users does not exist in database"));
        user.setRepassword(passwordEncoder.encode(user.getRepassword()));
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable( value = "id") Long userId, @RequestBody User userUpdate)
            throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!userUpdate.getEmail().isEmpty()) {
            user.setEmail(userUpdate.getEmail());
        }
        if (!userUpdate.getSurname().isEmpty()) {
            user.setSurname(userUpdate.getSurname());
        }
        if (!userUpdate.getName().isEmpty()) {
            user.setName(userUpdate.getName());
        }
        if (!userUpdate.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userUpdate.getPassword()));
        }
        if (!userUpdate.getCel().isEmpty()) {
            user.setCel(userUpdate.getCel());
        }
        if (!userUpdate.getUsername().isEmpty()){
            user.setUsername(userUpdate.getUsername());
        }
        if (!userUpdate.getUsername().isEmpty()) {
            user.setRepassword(passwordEncoder.encode(userUpdate.getRepassword()));
        }

        final User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable( value = "id") Long userId) {
        userRepository.deleteById(userId);
    }
}
