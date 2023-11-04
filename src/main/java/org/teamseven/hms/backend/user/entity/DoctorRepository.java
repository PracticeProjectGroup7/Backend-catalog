package org.teamseven.hms.backend.user.entity;

import org.springframework.data.repository.CrudRepository;
import org.teamseven.hms.backend.user.User;

import java.util.Optional;
import java.util.UUID;

public interface DoctorRepository extends CrudRepository<Doctor, UUID> {
    Optional<Doctor> findByUser(User user);

    Optional<Doctor> findByDoctorId(UUID doctorId);
}
