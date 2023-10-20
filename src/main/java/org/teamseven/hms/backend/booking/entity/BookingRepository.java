package org.teamseven.hms.backend.booking.entity;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface BookingRepository extends CrudRepository<Booking, UUID> {
}
