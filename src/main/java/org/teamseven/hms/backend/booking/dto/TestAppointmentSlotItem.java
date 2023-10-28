package org.teamseven.hms.backend.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.teamseven.hms.backend.booking.entity.TestStatus;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestAppointmentSlotItem {
    private String patientName;
    private TestStatus status;
    private String testName;
    private String reservedDate;
    private UUID bookingId;
}
