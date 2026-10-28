package com.thomasalves.library.resource;

import com.thomasalves.library.entities.Reservation;
import com.thomasalves.library.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;

@RestController
@RequestMapping("/reservations")
public class ReservationResource {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Reservation> create(
            @RequestParam Long userId,
            @RequestParam Long bookCopyId,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate scheduledPickupDate
            ){
        Reservation reservation = reservationService.reservation(userId, bookCopyId, scheduledPickupDate);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .replaceQuery(null)
                .path("/{id}")
                .buildAndExpand(reservation.getId())
                .toUri();


        return ResponseEntity.created(uri).body(reservation);
    }

}
