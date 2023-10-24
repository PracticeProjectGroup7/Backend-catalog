package org.teamseven.hms.backend.booking.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.teamseven.hms.backend.booking.entity.Booking;
import org.teamseven.hms.backend.booking.service.BookingService;

import java.util.NoSuchElementException;
import java.util.UUID;

// Validates booking ID is accessible by the logged in patient, i.e. the booking requested
// belongs to the patient themselves.
public class BookingIdParamBookingPatientAccessValidator extends BookingEndpointPatientAccessValidator {
    @Autowired
    private BookingService bookingService;

    protected BookingIdParamBookingPatientAccessValidator() {
        super();
        this.method = PatientDataRequestedMethod.BOOKING_ID_PATH;
    }

    @Override
    protected boolean isLoggedInPatientAuthorized(Object requestedResourceId, UUID loginUserId) {
        Booking booking = bookingService.getBookingById(UUID.fromString(requestedResourceId.toString()));
        if (booking == null) {
            throw new NoSuchElementException("Resource not found");
        }

        return booking.getPatientId().equals(loginUserId);
    }
}
