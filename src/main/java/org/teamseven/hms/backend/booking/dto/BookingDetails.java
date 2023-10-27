package org.teamseven.hms.backend.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public sealed class BookingDetails {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static final class Test extends BookingDetails{
        private String supportStaffName;
        private String testName;

        private String testResult;
        private String testId;
        private String testStatus;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static final class Appointment extends BookingDetails{
        private String doctorName;
        private String department;

        private String comments;
        private String appointmentId;
        private String appointmentStatus;
    }
}
