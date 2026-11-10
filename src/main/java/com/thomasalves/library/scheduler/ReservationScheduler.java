package com.thomasalves.library.scheduler;

import com.thomasalves.library.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReservationScheduler {

    @Autowired
    private ReservationService reservationService;

    @Scheduled(cron = "0 5 0 * * *", zone = "Europe/Lisbon")
    public void expireReservationsDaily() {
        reservationService.expireReservations();
    }
}
