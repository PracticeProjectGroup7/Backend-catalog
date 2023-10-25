package org.teamseven.hms.backend.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddBookingRequest {
    private UUID serviceId;
    private UUID patientId;
    private String appointmentDate;
    private String selectedSlot;
    private BookingType type;
}
