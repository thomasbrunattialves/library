package com.thomasalves.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class BookCopyInsertDTO {

    @NotNull
    @Positive
    private Long bookId;

    @NotBlank
    private String shelfLocation;

    public BookCopyInsertDTO() {
    }

    public BookCopyInsertDTO(Long bookId, String shelfLocation) {
        this.bookId = bookId;
        this.shelfLocation = shelfLocation;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getShelfLocation() {
        return shelfLocation;
    }

    public void setShelfLocation(String shelfLocation) {
        this.shelfLocation = shelfLocation;
    }
}
