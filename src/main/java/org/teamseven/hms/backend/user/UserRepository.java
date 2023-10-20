package org.teamseven.hms.backend.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;

public interface  UserRepository extends JpaRepository<User, UUID> {
    @Query(value = """
      select u from User u\s
      where u.email = :email and u.isactive = 1\s
      """)
    User findByEmail(String email);

}
