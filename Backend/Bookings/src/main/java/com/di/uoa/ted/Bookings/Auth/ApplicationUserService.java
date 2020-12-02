package com.di.uoa.ted.Bookings.Auth;

import com.di.uoa.ted.Bookings.Model.User;
import com.di.uoa.ted.Bookings.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class ApplicationUserService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    public ApplicationUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null){
            throw new UsernameNotFoundException(String.format("User %s Not Found", username));
        }

        return new ApplicationUserDetails(user);
    }
}
