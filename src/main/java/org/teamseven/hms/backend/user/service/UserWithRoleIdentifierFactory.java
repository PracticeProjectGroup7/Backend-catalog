package org.teamseven.hms.backend.user.service;

import org.teamseven.hms.backend.user.Role;
import org.teamseven.hms.backend.user.User;
import org.teamseven.hms.backend.user.dto.UserWithRoleIdentifier;

import java.util.UUID;


public class UserWithRoleIdentifierFactory {
    public static UserWithRoleIdentifier get(User user) {
        return switch(Role.valueOf(user.getType())) {
            case PATIENT -> new UserWithRoleIdentifier.Patient(UUID.randomUUID());
            case DOCTOR -> new UserWithRoleIdentifier.Doctor(UUID.randomUUID());
            case STAFF, ADMIN -> throw new RuntimeException(
                    "Can't derive role identified for " + user.getEmail()
            );
        };
    }
}
