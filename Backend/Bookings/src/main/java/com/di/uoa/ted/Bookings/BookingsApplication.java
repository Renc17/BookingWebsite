package com.di.uoa.ted.Bookings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileNotFoundException;

@SpringBootApplication
@Configuration
public class BookingsApplication {

	public static void main(String[] args) throws FileNotFoundException {
		SpringApplication.run(BookingsApplication.class, args);
	}
}
