package com.thomasalves.library.service;


import com.thomasalves.library.entities.BookCopy;
import com.thomasalves.library.entities.Loan;
import com.thomasalves.library.entities.User;
import com.thomasalves.library.entities.enums.Availability;
import com.thomasalves.library.entities.enums.BookStatus;
import com.thomasalves.library.repository.LoanRepository;
import com.thomasalves.library.service.exception.ResourceConflictException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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

    @Transactional
    public Loan loan(Long bookCopyId, Long userId) {
        User user = userService.findById(userId);
        BookCopy bookCopy = bookCopyService.findByIdForUpdate(bookCopyId);

        boolean reserved = reservationService.isReserved(bookCopyId, LocalDate.now());

        if (reserved) {
            throw new ResourceConflictException("Book copy is reserved and cannot be loaned.");
        }

        if (bookCopy.getBook().getStatus() != BookStatus.ACTIVE) {
            throw new ResourceConflictException("Book is not available for loan.");
        }

        if (bookCopy.getAvailability() != Availability.AVAILABLE) {
            throw new ResourceConflictException("Book copy is not available for loan.");
        }

        Loan loan = new Loan(user, bookCopy, LocalDate.now().plusDays(14));
        bookCopy.setAvailability(Availability.CHECKED_OUT);

        loanRepository.save(loan);

        return loan;

    }

}
