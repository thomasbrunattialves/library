package com.thomasalves.library.repository;


import com.thomasalves.library.entities.Reservation;
import com.thomasalves.library.entities.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsByBookCopy_IdAndScheduledPickupDateAndStatus(
            Long bookCopyId,
            LocalDate scheduledPickupDate,
            ReservationStatus status
    );
    Optional<Reservation> findFirstByBookCopy_IdAndScheduledPickupDateGreaterThanEqualAndStatusOrderByScheduledPickupDateAsc(
            Long bookCopyId,
            LocalDate currentDate,
            ReservationStatus status
    );
    @Query("""
    SELECT COUNT(r) > 0
    FROM Reservation r
    WHERE r.bookCopy.id = :bookCopyId
      AND r.status = :status
      AND r.scheduledPickupDate <= :pickupDate
      AND r.plannedReturnDate >= :pickupDate
    """)
    boolean existsReservationConflict(
            @Param("bookCopyId") Long bookCopyId,
            @Param("pickupDate") LocalDate pickupDate,
            @Param("status") ReservationStatus status
    );

}
