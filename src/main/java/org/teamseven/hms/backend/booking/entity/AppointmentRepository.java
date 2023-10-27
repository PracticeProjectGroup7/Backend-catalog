package org.teamseven.hms.backend.booking.entity;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AppointmentRepository extends CrudRepository<Appointment, UUID> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(
            "update Appointment a set " +
            "a.status = :status, " +
            "a.diagnosis = :comments " +
            "where a.appointmentId = :appointmentId"
    )
    int setStatusAndCommentsForAppointment(AppointmentStatus status, String comments, UUID appointmentId);

}
