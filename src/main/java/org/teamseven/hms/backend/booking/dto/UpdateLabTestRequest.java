package org.teamseven.hms.backend.booking.dto;

import jakarta.validation.constraints.NotNull;
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
    @NotNull private TestStatus status;
    @NotNull private String result;
}
