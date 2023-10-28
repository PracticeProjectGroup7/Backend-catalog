package org.teamseven.hms.backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.teamseven.hms.backend.user.Role;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateHospitalAccountRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String phoneNumber;
    private String nric;
    private String dateOfBirth;
    private String specialty;
    private Role role;
    private Double consultationFees;
    private Integer yearsOfExperience;
    private String address;
    private String password;
}
