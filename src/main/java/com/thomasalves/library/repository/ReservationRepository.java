package com.thomasalves.library.repository;


import com.thomasalves.library.entities.Reservation;
import com.thomasalves.library.entities.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsByBookCopy_IdAndScheduledPickupDateAndStatus(
            Long bookCopyId,
            LocalDate scheduledPickupDate,
            ReservationStatus status
    );
}
