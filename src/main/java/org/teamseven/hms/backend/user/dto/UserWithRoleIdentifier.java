package org.teamseven.hms.backend.user.dto;

import lombok.Getter;
import org.teamseven.hms.backend.user.Role;

import java.util.UUID;

@Getter
public sealed class UserWithRoleIdentifier {
    public UUID roleIdentifier;
    public Role role;
    public static final class Patient extends UserWithRoleIdentifier {
        public Patient(UUID patientId) {
            this.role = Role.PATIENT;
            this.roleIdentifier = patientId;
        }
    }
    public static final class Doctor extends UserWithRoleIdentifier {
        public Doctor(UUID doctorId) {
            super();
            this.roleIdentifier = doctorId;
            this.role = Role.DOCTOR;
        }
    }
}
