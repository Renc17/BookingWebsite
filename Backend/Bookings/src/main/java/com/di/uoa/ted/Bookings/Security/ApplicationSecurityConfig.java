package com.di.uoa.ted.Bookings.Security;

import com.di.uoa.ted.Bookings.Auth.ApplicationUserService;
import com.di.uoa.ted.Bookings.Jwt.JwtTokenVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    ApplicationUserService applicationUserService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public JwtTokenVerifier authenticationJwtTokenVerifier(){
        return new JwtTokenVerifier();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*", "/bookings/users/register", "/bookings/login", "/bookings/rentals/profile/*", "/bookings/rentals/profile/owner/*", "/bookings/images/**", "/bookings/book/**", "/bookings/rentals/comments/*").permitAll()
                .antMatchers("/bookings/users/**", "/bookings/rentals/**").hasAnyRole(ApplicationUserRole.USER.name(), ApplicationUserRole.ADMIN.name())
                .antMatchers("/bookings/rentals/owner", "/bookings/admin").hasRole(ApplicationUserRole.ADMIN.name())
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(authenticationJwtTokenVerifier(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }

}
