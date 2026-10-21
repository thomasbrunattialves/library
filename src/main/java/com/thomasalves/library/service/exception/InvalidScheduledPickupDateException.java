package com.thomasalves.library.service.exception;

public class InvalidScheduledPickupDateException extends RuntimeException {
    public InvalidScheduledPickupDateException(String message) {
        super(message);
    }
}
