package com.thomasalves.library.service;

import com.thomasalves.library.entities.Book;
import com.thomasalves.library.entities.BookCopy;
import com.thomasalves.library.entities.Reservation;
import com.thomasalves.library.entities.User;
import com.thomasalves.library.entities.enums.BookStatus;
import com.thomasalves.library.repository.ReservationRepository;
import com.thomasalves.library.service.exception.InvalidScheduledPickupDateException;
import com.thomasalves.library.service.exception.ObjectNotFoundException;
import com.thomasalves.library.service.exception.ResourceConflictException;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private BookCopyService bookCopyService;

    @Mock
    private UserService userService;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private ReservationService service;


    @Test
    void shouldReturnNextReservationLimitWhenItIsEarlier() {
        //Arrange
        LocalDate normalDueDate = LocalDate.of(2026, 11, 24);
        LocalDate earlierDueDate = LocalDate.of(2026, 11, 20);

        //Act
        LocalDate outcome = service.calculatePlannedReturnDate(normalDueDate, earlierDueDate);

        //Assert
        assertEquals(earlierDueDate, outcome);

    }

    @Test
    void shouldReturnNormalDueDateWhenItIsEarlier() {
        //Arrange
        LocalDate normalDueDate = LocalDate.of(2026, 11, 20);
        LocalDate earlierDueDate = LocalDate.of(2026, 11, 25);

        //Act
        LocalDate outcome = service.calculatePlannedReturnDate(normalDueDate, earlierDueDate);

        //Assert
        assertEquals(normalDueDate, outcome);

    }

    @Test
    void shouldThrowExceptionWhenNormalDueDateIsNull() {
        //Arrange
        LocalDate earlierDueDate = LocalDate.of(2026, 11, 25);
        LocalDate normalDueDate = null;

        //Act & Assert
        InvalidScheduledPickupDateException exception = assertThrows(InvalidScheduledPickupDateException.class, () -> {
            service.calculatePlannedReturnDate(normalDueDate, earlierDueDate);
        });

        //Assert
        assertEquals("Cannot calculate planned return date due to null values.", exception.getMessage());

    }

    @Test
    void shouldThrowExceptionWhenNextReservationLimitDateIsNull() {
        //Arrange
        LocalDate earlierDueDate = null;
        LocalDate normalDueDate = LocalDate.of(2026, 11, 25);

        //Act & Assert
        InvalidScheduledPickupDateException exception = assertThrows(InvalidScheduledPickupDateException.class, () -> {
            service.calculatePlannedReturnDate(normalDueDate, earlierDueDate);
        });

        //Assert
        assertEquals("Cannot calculate planned return date due to null values.", exception.getMessage());

    }

    @Test
    void shouldReturnReservationWhenIdExists() {
        // Arrange
        Long reservationId = 1L;
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        // Act
        Reservation foundReservation = service.findById(reservationId);

        // Assert
        assertEquals(reservation, foundReservation);
        verify(reservationRepository).findById(reservationId);

    }

    @Test
    void shouldThrowExceptionWhenReservationIdDoesNotExist() {
        // Arrange
        Long reservationId = 1L;
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        // Act & Assert
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> service.findById(reservationId));

        assertEquals("Reservation not found with id: " + reservationId, exception.getMessage());
        verify(reservationRepository).findById(reservationId);
    }

    @Test
    void shouldThrowExceptionWhenBookIsNotActive() {
        // Arrange
        Long userId = 1L;
        Long bookCopyId = 1L;

        User user = new User();
        user.setId(userId);

        Book book = new Book();
        BookCopy bookCopy = new BookCopy();
        bookCopy.setId(bookCopyId);

        book.setStatus(BookStatus.WITHDRAWING);
        bookCopy.setBook(book);

        LocalDate scheduledPickupDate = LocalDate.now().plusDays(1);
        when(bookCopyService.findByIdForUpdate(bookCopyId)).thenReturn(bookCopy);
        when(userService.findById(userId)).thenReturn(user);

        //Act
        ResourceConflictException exception = assertThrows(ResourceConflictException.class, () -> {
            service.reservation(userId, bookCopyId, scheduledPickupDate);
        });

        //Assert
        assertEquals("Book is not available for reservation.", exception.getMessage());
        verify(bookCopyService).findByIdForUpdate(bookCopyId);
        verify(userService).findById(userId);
        verify(reservationRepository,never()).save(any());
    }



}