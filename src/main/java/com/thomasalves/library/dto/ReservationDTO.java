package com.thomasalves.library.dto;

import com.thomasalves.library.entities.Reservation;
import com.thomasalves.library.entities.enums.ReservationStatus;

import java.io.Serializable;
import java.time.LocalDate;

public class ReservationDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long userId;
    private Long bookCopyId;

    private LocalDate scheduledPickupDate;
    private LocalDate plannedReturnDate;
    private ReservationStatus status;


    public ReservationDTO (Reservation reservation) {
        this.id = reservation.getId();
        this.userId = reservation.getUser().getId();
        this.bookCopyId = reservation.getBookCopy().getId();
        this.scheduledPickupDate = reservation.getScheduledPickupDate();
        this.plannedReturnDate = reservation.getPlannedReturnDate();
        this.status = reservation.getStatus();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookCopyId() {
        return bookCopyId;
    }

    public void setBookCopyId(Long bookCopyId) {
        this.bookCopyId = bookCopyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getScheduledPickupDate() {
        return scheduledPickupDate;
    }

    public void setScheduledPickupDate(LocalDate scheduledPickupDate) {
        this.scheduledPickupDate = scheduledPickupDate;
    }

    public LocalDate getPlannedReturnDate() {
        return plannedReturnDate;
    }

    public void setPlannedReturnDate(LocalDate plannedReturnDate) {
        this.plannedReturnDate = plannedReturnDate;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}
