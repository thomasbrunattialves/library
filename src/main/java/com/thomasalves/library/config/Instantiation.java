package com.thomasalves.library.config;

import com.thomasalves.library.entities.User;
import com.thomasalves.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Arrays;

@Configuration
public class Instantiation implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {



        userRepository.deleteAll();

        User u1 = new User("John Smith", LocalDate.of(1992, 5, 14),
                "john.smith@email.com", "912345678", "Lisbon, Portugal");

        User u2 = new User("Emma Johnson", LocalDate.of(1998, 11, 3),
                "emma.johnson@email.com", "913456789", "Porto, Portugal");

        User u3 = new User("Michael Brown", LocalDate.of(1987, 2, 21),
                "michael.brown@email.com", "914567890", "Coimbra, Portugal");

        User u4 = new User("Sophia Davis", LocalDate.of(2001, 7, 9),
                "sophia.davis@email.com", "915678901", "Braga, Portugal");

        User u5 = new User("Daniel Wilson", LocalDate.of(1995, 9, 27),
                "daniel.wilson@email.com", "916789012", "Figueira da Foz, Portugal");

        userRepository.saveAll(Arrays.asList(u1, u2, u3, u4, u5));
    }
}
