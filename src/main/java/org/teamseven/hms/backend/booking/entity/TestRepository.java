package org.teamseven.hms.backend.booking.entity;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TestRepository extends CrudRepository<Test, UUID> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(
            "update Test t set " +
                    "t.status = :status " +
                    "where t.testId = :testId"
    )
    int setTestStatus(TestStatus status, UUID testId);
}
