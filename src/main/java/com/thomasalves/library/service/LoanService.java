package com.thomasalves.library.service;


import com.thomasalves.library.entities.BookCopy;
import com.thomasalves.library.entities.Loan;
import com.thomasalves.library.entities.Reservation;
import com.thomasalves.library.entities.User;
import com.thomasalves.library.entities.enums.Availability;
import com.thomasalves.library.entities.enums.BookStatus;
import com.thomasalves.library.entities.enums.LoanStatus;
import com.thomasalves.library.entities.enums.ReservationStatus;
import com.thomasalves.library.repository.LoanRepository;
import com.thomasalves.library.service.exception.ObjectNotFoundException;
import com.thomasalves.library.service.exception.ResourceConflictException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private BookCopyService bookCopyService;
    @Autowired
    private UserService userService;
    @Autowired
    private ReservationService reservationService;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Loan loan(Long bookCopyId, Long userId) {
        User user = userService.findById(userId);
        BookCopy bookCopy = bookCopyService.findByIdForUpdate(bookCopyId);

        LocalDate today = LocalDate.now();
        LocalDate dueDate = today.plusDays(14);

        if (bookCopy.getBook().getStatus() != BookStatus.ACTIVE) {
            throw new ResourceConflictException("Book is not available for loan.");
        }


        if (bookCopy.getAvailability() != Availability.AVAILABLE) {
            throw new ResourceConflictException("Book copy is not available for loan.");
        }

        boolean hasConflictReservation = reservationService.hasReservationConflict(bookCopyId, today);
        Optional<Reservation> nextReservation = reservationService.findNextPendingReservation(bookCopyId, today);

        if (hasConflictReservation) {
            throw new ResourceConflictException("Book copy is reserved and cannot be loaned.");
        }

        if (nextReservation.isPresent()) {
            if (nextReservation.get().getScheduledPickupDate().isAfter(today)) {
                dueDate = reservationService.calculatePlannedReturnDate(
                        dueDate,
                        nextReservation.get().getScheduledPickupDate().minusDays(1)
                );
            }
        }

        if (!today.isBefore(dueDate)) {
            throw new ResourceConflictException(
                    "Loan must have at least one day before the return date."
            );
        }

        Loan loan = new Loan(user, bookCopy, dueDate);
        bookCopy.setAvailability(Availability.CHECKED_OUT);

        loanRepository.save(loan);

        return loan;

    }

    @Transactional
    public Loan returnLoan(Long loanId) {
        Loan existingLoan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Loan not found with id: " + loanId
                ));
        BookCopy bookCopy = bookCopyService.findByIdForUpdate(
                existingLoan.getBookCopy().getId()
        );
       Loan loan = loanRepository.findByIdForUpdate(loanId)
                .orElseThrow(() -> new ObjectNotFoundException("Loan not found with id: " + loanId));

        entityManager.refresh(loan);

        if (loan.getStatus() != LoanStatus.ACTIVE) {
            throw new ResourceConflictException("Loan is not active.");
        }

        loan.setStatus(LoanStatus.RETURNED);
        loan.setReturnDate(LocalDate.now());
        bookCopy.setAvailability(Availability.AVAILABLE);
        loanRepository.save(loan);

        return loan;

    }


    @Transactional
    public Loan pickupReservation(Long reservationId) {
        Reservation reservation = reservationService.findById(reservationId);
        BookCopy bookCopy = bookCopyService.findByIdForUpdate(reservation.getBookCopy().getId());
        entityManager.refresh(reservation);
        if (bookCopy.getBook().getStatus() != BookStatus.ACTIVE) {
            throw new ResourceConflictException(
                    "Book is not available for pickup."
            );
        }

        if(reservation.getStatus() != ReservationStatus.PENDING) {
            throw new ResourceConflictException("Reservation is not pending.");
        }
        if (bookCopy.getAvailability() != Availability.AVAILABLE) {
            throw new ResourceConflictException(
                    "Book copy is not available for pickup."
            );
        }
        if(reservationService.findActiveLoan(bookCopy).isPresent()) {
            throw new ResourceConflictException("There is an active loan for this book copy.");
        }

        LocalDate today = LocalDate.now();

        if (reservation.getScheduledPickupDate().isBefore(today)) {
            throw new ResourceConflictException(
                    "Cannot pick up reservation. Scheduled pickup date has already passed."
            );
        }

        if (!today.isBefore(reservation.getPlannedReturnDate())) {
            throw new ResourceConflictException(
                    "Reservation return date has already expired."
            );
        }
        if(reservationService.existsPickupConflict(bookCopy.getId(), reservation.getId(), today, reservation.getPlannedReturnDate(), ReservationStatus.PENDING)) {
            throw new ResourceConflictException(
                    "Cannot pick up reservation. There is a conflicting reservation."
            );
        }



        Loan loan = new Loan(reservation.getUser(), bookCopy, reservation.getPlannedReturnDate());
        reservation.setStatus(ReservationStatus.COMPLETED);
        reservation.setActualPickupDate(today);
        bookCopy.setAvailability(Availability.CHECKED_OUT);
        loanRepository.save(loan);
        return loan;

    }

}
