package org.teamseven.hms.backend.catalog.entity;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ServiceRepository extends CrudRepository<Service, UUID> {
}
