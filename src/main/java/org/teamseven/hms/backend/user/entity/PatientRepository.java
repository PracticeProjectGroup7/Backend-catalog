package org.teamseven.hms.backend.user.entity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.teamseven.hms.backend.user.User;

import java.util.UUID;

public interface PatientRepository extends CrudRepository<Patient, UUID> {

    @Query(value = """
      select p from Patient p\s
      where p.user = :user and p.isActive = 1\s
      """)
    Patient findByUser(User user);
}