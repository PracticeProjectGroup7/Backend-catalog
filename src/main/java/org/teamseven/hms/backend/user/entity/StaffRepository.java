package org.teamseven.hms.backend.user.entity;

import org.springframework.data.repository.CrudRepository;
import org.teamseven.hms.backend.user.User;

import java.util.Optional;
import java.util.UUID;

public interface StaffRepository extends CrudRepository<Staff, UUID> {

    Optional<Staff> findByUser(User user);
}
