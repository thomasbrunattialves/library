package com.thomasalves.library.config;

import com.thomasalves.library.entities.Author;
import com.thomasalves.library.entities.Book;
import com.thomasalves.library.entities.BookCopy;
import com.thomasalves.library.entities.User;
import com.thomasalves.library.entities.enums.Availability;
import com.thomasalves.library.entities.enums.Category;
import com.thomasalves.library.repository.AuthorRepository;
import com.thomasalves.library.repository.BookCopyRepository;
import com.thomasalves.library.repository.BookRepository;
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

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Autowired
    private AuthorRepository authorRepository;


    @Override
    public void run(String... args) throws Exception {

        bookCopyRepository.deleteAll();
        bookRepository.deleteAll();
        authorRepository.deleteAll();
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

        Author a1 = new Author(
                "Robert C. Martin",
                "American",
                1952
        );

        Author a2 = new Author(
                "Joshua Bloch",
                "American",
                1961
        );

        Author a3 = new Author(
                "Erich Gamma",
                "Swiss",
                1961
        );

        Author a4 = new Author(
                "Richard Helm",
                "Australian",
                1956
        );

        authorRepository.saveAll(Arrays.asList(a1, a2, a3, a4));

        Book b1 = new Book(
                "Clean Code",
                Arrays.asList(a1),
                Category.PROGRAMMING,
                "English",
                464,
                1
        );

        Book b2 = new Book(
                "Effective Java",
                Arrays.asList(a2),
                Category.PROGRAMMING,
                "English",
                416,
                3
        );

        Book b3 = new Book(
                "Design Patterns",
                Arrays.asList(a3, a4),
                Category.PROGRAMMING,
                "English",
                395,
                1
        );

        bookRepository.saveAll(Arrays.asList(b1, b2, b3));

        BookCopy bc1 = new BookCopy(
                b1,
                Availability.AVAILABLE,
                "A01"
        );

        BookCopy bc2 = new BookCopy(
                b1,
                Availability.CHECKED_OUT,
                "A02"
        );

        BookCopy bc3 = new BookCopy(
                b2,
                Availability.AVAILABLE,
                "B01"
        );

        BookCopy bc4 = new BookCopy(
                b3,
                Availability.RESERVED,
                "C05"
        );

        bookCopyRepository.saveAll(Arrays.asList(bc1, bc2, bc3, bc4));




    }
}
