package org.teamseven.hms.backend.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DoctorProfile {
    private UUID doctorId;
    private String specialty;
    private String yearOfExperience;
}
