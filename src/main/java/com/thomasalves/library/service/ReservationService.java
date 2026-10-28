package com.thomasalves.library.service;

import com.thomasalves.library.entities.BookCopy;
import com.thomasalves.library.entities.Loan;
import com.thomasalves.library.entities.Reservation;
import com.thomasalves.library.entities.User;
import com.thomasalves.library.entities.enums.BookStatus;
import com.thomasalves.library.entities.enums.LoanStatus;
import com.thomasalves.library.entities.enums.ReservationStatus;
import com.thomasalves.library.repository.ReservationRepository;
import com.thomasalves.library.service.exception.InvalidScheduledPickupDateException;
import com.thomasalves.library.service.exception.ResourceConflictException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

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

        if (!(copy.getBook().getStatus() == BookStatus.ACTIVE)) {
            throw new ResourceConflictException("Book is not available for reservation.");
        }

        if (hasReservationConflict(bookCopyId, scheduledPickupDate)) {
            throw new ResourceConflictException(
                    "Book copy is unavailable for the selected date."
            );
        }

        LocalDate reservendDate = scheduledPickupDate.plusDays(14);;

        Optional<Loan> activeLoan = findActiveLoan(copy);
        if (activeLoan.isPresent()) {
            LocalDate dueDate = activeLoan.get().getDueDate();

            if (scheduledPickupDate.isBefore(dueDate) || scheduledPickupDate.isEqual(dueDate)) {
                throw new InvalidScheduledPickupDateException("Scheduled pickup date cannot be before the due date.");
            }
        }

        Optional<Reservation> nextReservation = findNextPendingReservation(bookCopyId, scheduledPickupDate);

        if(nextReservation.isPresent()) {
                reservendDate = nextReservation.get().getScheduledPickupDate().minusDays(1);
        }

        LocalDate calculatePlannedReturnDate = calculatePlannedReturnDate(scheduledPickupDate.plusDays(14), reservendDate);
        if (scheduledPickupDate.isAfter(calculatePlannedReturnDate)) {
            throw new InvalidScheduledPickupDateException("Scheduled pickup date cannot be after the planned return date.");
        }

        Reservation reservation = new Reservation(user, copy, scheduledPickupDate);

        reservation.setPlannedReturnDate(calculatePlannedReturnDate);
        reservationRepository.save(reservation);
        return reservation;
    }


    public boolean isReserved(Long bookCopyId, LocalDate scheduledPickupDate) {
        return reservationRepository
                .existsByBookCopy_IdAndScheduledPickupDateAndStatus(
                        bookCopyId,
                        scheduledPickupDate,
                        ReservationStatus.PENDING
                );
    }

    public Optional<Reservation> findNextPendingReservation(Long bookCopyId, LocalDate scheduledPickupDate) {
        return reservationRepository
                .findFirstByBookCopy_IdAndScheduledPickupDateGreaterThanEqualAndStatusOrderByScheduledPickupDateAsc(
                        bookCopyId,
                        scheduledPickupDate,
                        ReservationStatus.PENDING
                );
    }

    public boolean hasReservationConflict(Long bookCopyId, LocalDate scheduledPickupDate) {
        if (scheduledPickupDate == null) {
            return true;
        }
        boolean reserved = reservationRepository.existsReservationConflict(
                bookCopyId,
                scheduledPickupDate,
                ReservationStatus.PENDING
        );
        if (reserved) {
            return true;
        }
        if (scheduledPickupDate.isBefore(LocalDate.now())) {
            return true;
        }

        return false;
    }

    private LocalDate calculatePlannedReturnDate(LocalDate normalDueDate, LocalDate nextReservationLimit) {
        if (normalDueDate == null || nextReservationLimit == null) {
            throw new InvalidScheduledPickupDateException("Cannot calculate planned return date due to null values.");
        }
        if (normalDueDate.isBefore(nextReservationLimit)) {
            return normalDueDate;
        }
        return nextReservationLimit;
    }

    private Optional<Loan> findActiveLoan(BookCopy copy) {
        Optional<Loan> activeLoan = copy.getLoans().stream()
                .filter(loan -> loan.getStatus() == LoanStatus.ACTIVE)
                .findFirst();
        return activeLoan;
    }



}


