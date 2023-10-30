package org.teamseven.hms.backend.user.entity;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface DoctorRepository extends CrudRepository<Doctor, UUID> {
    Optional<Doctor> findByUserId(UUID userId);
}
