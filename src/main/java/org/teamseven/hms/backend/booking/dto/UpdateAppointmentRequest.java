package org.teamseven.hms.backend.booking.dto;

import lombok.*;
import org.teamseven.hms.backend.booking.entity.AppointmentStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UpdateAppointmentRequest {
    private String comments;
    private AppointmentStatus status;
}
