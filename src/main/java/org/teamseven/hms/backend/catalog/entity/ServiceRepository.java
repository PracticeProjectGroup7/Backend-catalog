package org.teamseven.hms.backend.catalog.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

//@Repository
public interface ServiceRepository extends CrudRepository<Service, String> {
    @Query(value = "{ 'type': ?0, 'isActive': 1 }", sort = "{ 'name': 1 }")
    Page<Service> findAvailableServices(
            @Param("serviceType") String serviceType,
            Pageable pageable
    );

    Optional<Service> findByDoctorid(String doctorId);
}
