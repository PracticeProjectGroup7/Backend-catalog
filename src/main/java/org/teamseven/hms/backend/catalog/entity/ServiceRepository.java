package org.teamseven.hms.backend.catalog.entity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.teamseven.hms.backend.user.User;

import java.util.UUID;

public interface ServiceRepository extends CrudRepository<Service, UUID> {
    @Query(value = """
      select s from Service s\s
      where s.serviceId = :serviceId and s.isActive = 1\s
      """)
    Service findByServiceId(UUID serviceId);

}
