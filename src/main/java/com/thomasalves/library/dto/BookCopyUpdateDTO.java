package com.thomasalves.library.dto;

import jakarta.validation.constraints.NotBlank;

public class BookCopyUpdateDTO {

    @NotBlank
    private String shelfLocation;

    public BookCopyUpdateDTO() {
    }

    public BookCopyUpdateDTO(String shelfLocation) {
        this.shelfLocation = shelfLocation;

    }

    public String getShelfLocation() {
        return shelfLocation;
    }

    public void setShelfLocation(String shelfLocation) {
        this.shelfLocation = shelfLocation;
    }
}

