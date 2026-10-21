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

        if (scheduledPickupDate == null) {
            throw new InvalidScheduledPickupDateException("Scheduled pickup date cannot be null.");
        }

        if (scheduledPickupDate.isBefore(LocalDate.now())) {
            throw new  InvalidScheduledPickupDateException("Scheduled pickup date cannot be in the past.");
        }

        boolean reserved = isReserved(bookCopyId, scheduledPickupDate);

        if (reserved){
            throw new ResourceConflictException("Book copy is already reserved for the scheduled pickup date.");
        }

        if (!(copy.getBook().getStatus() == BookStatus.ACTIVE)) {
            throw new ResourceConflictException("Book is not available for reservation.");
        }

        Optional<Loan> activeLoan = copy.getLoans().stream()
                .filter(loan -> loan.getStatus() == LoanStatus.ACTIVE)
                .findFirst();

        if (activeLoan.isPresent()) {
            LocalDate dueDate = activeLoan.get().getDueDate();
            if (scheduledPickupDate.isAfter(dueDate)) {
                Reservation reserve = new Reservation(user, copy, scheduledPickupDate);
                reservationRepository.save(reserve);
                return reserve;
            }
            if (scheduledPickupDate.isBefore(dueDate) || scheduledPickupDate.isEqual(dueDate)) {
                throw new InvalidScheduledPickupDateException("Scheduled pickup date cannot be before the due date.");
            }
        }

        Reservation reservation = new Reservation(user, copy, scheduledPickupDate);
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

}
