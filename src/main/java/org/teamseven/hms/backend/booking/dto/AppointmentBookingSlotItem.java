package org.teamseven.hms.backend.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.teamseven.hms.backend.booking.entity.AppointmentStatus;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentBookingSlotItem {
    private String patientName;
    private AppointmentStatus status;
    private String reservedDate;
    private UUID bookingId;
}
