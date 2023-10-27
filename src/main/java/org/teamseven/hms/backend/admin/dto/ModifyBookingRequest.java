package org.teamseven.hms.backend.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModifyBookingRequest {
    private String patientId;
    private String serviceId;
    private String reservedDate;
    private String newSlot;
}
