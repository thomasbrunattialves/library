package com.thomasalves.library.dto;

import com.thomasalves.library.entities.BookCopy;
import com.thomasalves.library.entities.enums.Availability;

import java.io.Serializable;

    public class BookCopyDTO implements Serializable {

        private Long id;
        private Availability availability;
        private String shelfLocation;
        private Long bookId;

        public BookCopyDTO(){

        }

        public BookCopyDTO(BookCopy bookCopy){
            this.id = bookCopy.getId();
            this.availability = bookCopy.getAvailability();
            this.shelfLocation = bookCopy.getShelfLocation();
            this.bookId = bookCopy.getBook().getId();
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getShelfLocation() {
            return shelfLocation;
        }

        public void setShelfLocation(String shelfLocation) {
            this.shelfLocation = shelfLocation;
        }

        public Availability getAvailability() {
            return availability;
        }

        public void setAvailability(Availability availability) {
            this.availability = availability;
        }

        public Long getBookId() {
            return bookId;
        }

        public void setBookId(Long bookId) {
            this.bookId = bookId;
        }
    }

