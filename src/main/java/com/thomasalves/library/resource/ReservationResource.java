package com.thomasalves.library.resource;

import com.thomasalves.library.dto.ReservationDTO;
import com.thomasalves.library.entities.Reservation;
import com.thomasalves.library.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;

@RestController
@RequestMapping("/reservations")
public class ReservationResource {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationDTO> create(
            @RequestParam Long userId,
            @RequestParam Long bookCopyId,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate scheduledPickupDate
            ){
        Reservation reservation = reservationService.reservation(userId, bookCopyId, scheduledPickupDate);
        ReservationDTO reservationDTO = new ReservationDTO(reservation);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .replaceQuery(null)
                .path("/{id}")
                .buildAndExpand(reservation.getId())
                .toUri();


        return ResponseEntity.created(uri).body(reservationDTO);
    }
    @PatchMapping("/{reservationId}/cancel")
    public ResponseEntity<ReservationDTO> cancel(@PathVariable Long reservationId) {
        Reservation reservation = reservationService.cancelReservation(reservationId);
        ReservationDTO reservationDTO = new ReservationDTO(reservation);
        return ResponseEntity.ok(reservationDTO);
    }

}
