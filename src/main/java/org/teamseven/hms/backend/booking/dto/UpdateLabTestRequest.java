package org.teamseven.hms.backend.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.teamseven.hms.backend.booking.entity.TestStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLabTestRequest {
    private TestStatus status;
}
