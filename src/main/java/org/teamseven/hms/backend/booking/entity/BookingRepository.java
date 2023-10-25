package org.teamseven.hms.backend.booking.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface BookingRepository extends CrudRepository<Booking, UUID> {
    @Query(
            value = "SELECT * " +
                    "from bookings where patient_id = UUID_TO_BIN(:patientId) " +
                    "ORDER BY reserved_date desc, created_at DESC",
            nativeQuery=true
    )
    Page<Booking> findPatientBookingsWithPagination(
            @Param("patientId") String patientId,
            Pageable pageable
    );
}
