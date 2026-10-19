package com.thomasalves.library.service;

import com.thomasalves.library.entities.BookCopy;
import com.thomasalves.library.entities.Reservation;
import com.thomasalves.library.entities.User;
import com.thomasalves.library.entities.enums.Availability;
import com.thomasalves.library.entities.enums.BookStatus;
import com.thomasalves.library.entities.enums.ReservationStatus;
import com.thomasalves.library.repository.ReservationRepository;
import com.thomasalves.library.service.exception.ResourceConflictException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private BookCopyService bookCopyService;

    @Autowired
    private UserService userService;


    @Transactional
    public Reservation reservation(Long userId, Long bookCopyId, LocalDate scheduledPickupDate) {
        BookCopy copy = bookCopyService.findByIdForUpdate(bookCopyId);
        User user = userService.findById(userId);

        if (scheduledPickupDate == null) {
            throw new ResourceConflictException("Scheduled pickup date cannot be null.");
        }

        if (scheduledPickupDate.isBefore(LocalDate.now())) {
            throw new ResourceConflictException("Scheduled pickup date cannot be in the past.");
        }

        boolean reserved = reservationRepository
                .existsByBookCopy_IdAndScheduledPickupDateAndStatus(
                        copy.getId(),
                        scheduledPickupDate,
                        ReservationStatus.PENDING
                );
        if (reserved){
            throw new ResourceConflictException("Book copy is already reserved for the scheduled pickup date.");
        }
        if (copy.getAvailability() != Availability.AVAILABLE) {
            throw new ResourceConflictException("Book copy is not available for reservation.");
        }
        if (!(copy.getBook().getStatus() == BookStatus.ACTIVE)) {
            throw new ResourceConflictException("Book is not available for reservation.");
        }


        Reservation reservation = new Reservation(user, copy, scheduledPickupDate);

        reservationRepository.save(reservation);


        return reservation;
    }

}
