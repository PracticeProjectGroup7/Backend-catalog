package org.teamseven.hms.backend.user.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface StaffRepository extends CrudRepository<Staff, UUID> {

    @Query(
            value = "select s from Staff s"
    )
    Page<Staff> getStaff(Pageable pageable);

}
