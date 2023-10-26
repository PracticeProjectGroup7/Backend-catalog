package org.teamseven.hms.backend.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingInfoResponse {
    private BookingType bookingType;
    private String bookingDate;
    private String[] slots;
    private BookingDetails details;
    private PatientDataBookingDetails patientDetails;
}
