package com.thomasalves.library.dto;

public class BookCopyInsertDTO {

    private Long bookId;
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
