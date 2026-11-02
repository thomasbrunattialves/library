package com.thomasalves.library.dto;

import com.thomasalves.library.entities.Loan;
import com.thomasalves.library.entities.enums.LoanStatus;

import java.io.Serializable;
import java.time.LocalDate;

public class LoanDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String user;
    private String bookCopy;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LoanStatus status;


    public LoanDTO(Loan loan) {
        this.id = loan.getId();
        this.user = loan.getUser().getName();
        this.bookCopy = loan.getBookCopy().getBook().getTitle() + " - " + loan.getBookCopy().getId();
        this.loanDate = loan.getLoanDate();
        this.dueDate = loan.getDueDate();
        this.status = loan.getStatus();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getBookCopy() {
        return bookCopy;
    }

    public void setBookCopy(String bookCopy) {
        this.bookCopy = bookCopy;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}