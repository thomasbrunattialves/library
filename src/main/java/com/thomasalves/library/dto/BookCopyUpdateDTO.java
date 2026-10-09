package com.thomasalves.library.dto;

import com.thomasalves.library.entities.enums.Availability;

public class BookCopyUpdateDTO {

    private String shelfLocation;
    private Availability availability;

    public BookCopyUpdateDTO() {
    }

    public BookCopyUpdateDTO(String shelfLocation, Availability availability) {
        this.shelfLocation = shelfLocation;
        this.availability = availability;

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
}
