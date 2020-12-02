package com.di.uoa.ted.Bookings.Controller;

import com.di.uoa.ted.Bookings.Jwt.JwtProvider;
import com.di.uoa.ted.Bookings.Model.AuthBody;
import com.di.uoa.ted.Bookings.Model.Role;
import com.di.uoa.ted.Bookings.Model.User;
import com.di.uoa.ted.Bookings.Repository.RoleRepository;
import com.di.uoa.ted.Bookings.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("bookings")
public class AuthController{

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/login")
    public Map<Object, Object> login(@RequestBody AuthBody data){

        System.out.println(data.getUsername());

        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            data.getUsername(),
                            data.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtProvider.generateJwtToken(authentication);

            User user = userRepository.findByUsername(data.getUsername());
            Map<Object, Object> model = new HashMap<>();
            model.put("user", user);
            model.put("token", jwt);

            return model;
        }catch (AuthenticationException e){
            throw new BadCredentialsException("Invalid username/password");
        }
    }

    @PostMapping("/users/register")
    public User register(@RequestBody User user){
        Optional<User> sameUsernameUser;
        sameUsernameUser = userRepository.findAll().stream().filter(user1 ->
                user1.getUsername().equals(user.getUsername())
        ).findAny();
        if(sameUsernameUser.isPresent()){
            System.out.println("Invalid Username : Choose another " + user.getUsername());
            return null;
        }

        List<User> onInstallation = userRepository.findAll();
        Role role;

        if (onInstallation.isEmpty()){
            role = roleRepository.findByName("ROLE_ADMIN");
        }else {
            role = roleRepository.findByName("ROLE_USER");
        }
        user.setRoles(role);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRepassword(passwordEncoder.encode(user.getRepassword()));
        return userRepository.save(user);
    }
}
