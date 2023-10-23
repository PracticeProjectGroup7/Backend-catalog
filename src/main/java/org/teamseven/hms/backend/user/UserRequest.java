package org.teamseven.hms.backend.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String email;
    private String nric;
    private String type;
    private String address;
    private String gender;
    private String role;
    private String phone;
}
