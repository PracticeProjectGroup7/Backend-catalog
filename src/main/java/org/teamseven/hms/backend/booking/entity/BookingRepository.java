package org.teamseven.hms.backend.booking.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
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

    @Query(
            value = "select * " +
                    "from bookings b where b.reserved_date = :appointmentDate and b.slots = :slot " +
                    "and b.service_id = UUID_TO_BIN(:serviceId)",
            nativeQuery=true
    )
    Optional<Booking> findByAppointmentDate(String appointmentDate, String slot, String serviceId);

    @Query(
            value = "select * " +
                    "from bookings b where b.reserved_date = :appointmentDate and b.service_id = UUID_TO_BIN(:serviceId)",
            nativeQuery=true
    )
    Optional<Booking> checkTestExists(String appointmentDate, String serviceId);

    @Query(
            value = "SELECT * from bookings where booking_id = UUID_TO_BIN(:bookingId) ",
            nativeQuery=true
    )
    Optional<Booking> findByBookingId(String bookingId);

    List<Booking> findByServiceIdAndReservedDate(UUID serviceId, String reservedDate);

    @Query(value = "select * from bookings where service_id = UUID_TO_BIN(:serviceId) and reserved_date = :reservedDate and slots = :slot", nativeQuery = true)
    Optional<Booking> findByServiceIdAndReservedDateAndSlot(String serviceId, String reservedDate, String slot);

    @Modifying(clearAutomatically = true)
    @Query(value = "update bookings set reserved_date = :newReservedDate, slots = :newSlot where patient_id = UUID_TO_BIN(:patientId) and service_id = UUID_TO_BIN(:serviceId) and slots = :oldSlot and reserved_date = :oldReservedDate", nativeQuery = true)
    int updateBooking(String patientId, String serviceId, String oldReservedDate, String oldSlot, String newReservedDate, String newSlot);

    @Modifying(clearAutomatically = true)
    @Query(value = "update bookings set reserved_date = :newReservedDate where patient_id = UUID_TO_BIN(:patientId) and service_id = UUID_TO_BIN(:serviceId) and reserved_date = :oldReservedDate and test_id is not null", nativeQuery = true)
    int updateTest(String patientId, String serviceId, String oldReservedDate, String newReservedDate);


    @Modifying(clearAutomatically = true)
    @Query(value = "update bookings set bill_status = :billStatus, paid_at = :paidAt where booking_id = UUID_TO_BIN(:bookingId)", nativeQuery = true)
    int updateBillStatus(String billStatus, String paidAt, String bookingId);

    @Query(
            value = "SELECT * " +
                    "from bookings where test_id IS NOT NULL " +
                    "AND reserved_date >= :reservedDate " +
                    "ORDER BY reserved_date, created_at",
            nativeQuery=true
    )
    Page<Booking> findUpcomingTestBookings(
            String reservedDate,
            Pageable pageable
    );

    @Query(
            value = "SELECT * " +
                    "from bookings where service_id = UUID_TO_BIN(:serviceId) " +
                    "AND reserved_date >= :reservedDate " +
                    "ORDER BY reserved_date, created_at",
            nativeQuery=true
    )
    Page<Booking> findUpcomingServiceBookings(
            String serviceId,
            String reservedDate,
            Pageable pageable
    );
}
