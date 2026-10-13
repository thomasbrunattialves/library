package com.thomasalves.library.entities;

import com.thomasalves.library.entities.enums.BookStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_book_status_history")
public class BookStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Enumerated(EnumType.STRING)
    private BookStatus previousStatus;
    @Enumerated(EnumType.STRING)
    private BookStatus newStatus;
    private LocalDateTime changedAt;
    private String reason;

    public BookStatusHistory() {

    }

    public BookStatusHistory(Book book, BookStatus previousStatus, BookStatus newStatus, String reason) {
        this.book = book;
        this.previousStatus = previousStatus;
        this.newStatus = newStatus;
        this.reason = reason;
        this.changedAt = LocalDateTime.now();
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

    public BookStatus getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(BookStatus previousStatus) {
        this.previousStatus = previousStatus;
    }

    public BookStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(BookStatus newStatus) {
        this.newStatus = newStatus;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(LocalDateTime changedAt) {
        this.changedAt = changedAt;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }



}
