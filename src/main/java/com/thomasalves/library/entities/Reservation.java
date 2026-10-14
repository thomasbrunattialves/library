package com.thomasalves.library.entities;

import com.thomasalves.library.entities.enums.ReservationStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reservation_user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "reservation_book_copy_id", nullable = false)
    private BookCopy bookCopy;

    private LocalDate reservationDate = LocalDate.now();
    private LocalDateTime scheduledPickupDate;
    private LocalDateTime actualPickupDate;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    public Reservation() {
    }

    public Reservation(User user, BookCopy bookCopy,LocalDateTime scheduledPickupDate, ReservationStatus status) {
        this.user = user;
        this.bookCopy = bookCopy;
        this.scheduledPickupDate = scheduledPickupDate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public void setBookCopy(BookCopy bookCopy) {
        this.bookCopy = bookCopy;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public LocalDateTime getScheduledPickupDate() {
        return scheduledPickupDate;
    }

    public void setScheduledPickupDate(LocalDateTime scheduledPickupDate) {
        this.scheduledPickupDate = scheduledPickupDate;
    }

    public LocalDateTime getActualPickupDate() {
        return actualPickupDate;
    }

    public void setActualPickupDate(LocalDateTime actualPickupDate) {
        this.actualPickupDate = actualPickupDate;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
