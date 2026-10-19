package com.thomasalves.library.entities;

import com.thomasalves.library.entities.enums.Availability;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_book_copy")
public class BookCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Enumerated(EnumType.STRING)
    private Availability availability;
    private String shelfLocation;

    @OneToMany(mappedBy = "bookCopy")
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "bookCopy")
    private List<Loan> loans = new ArrayList<>();

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Reservation reservation) {
        this.reservations.add(reservation);
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(Loan loans) {
        this.loans.add(loans);
    }


    public BookCopy() {
    }

    public BookCopy(Book book, Availability availability, String shelfLocation) {
        this.book = book;
        this.availability = availability;
        this.shelfLocation = shelfLocation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    public String getShelfLocation() {
        return shelfLocation;
    }

    public void setShelfLocation(String shelfLocation) {
        this.shelfLocation = shelfLocation;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookCopy bookCopy = (BookCopy) o;
        return id != null && id.equals(bookCopy.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}


