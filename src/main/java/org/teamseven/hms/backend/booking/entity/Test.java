package org.teamseven.hms.backend.booking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE tests SET is_active = false WHERE test_id=?")
@Where(clause = "is_active = true")
@Table(name = "tests")
public class Test {
    @Id
    @Column(name="test_id", insertable=false)
    @GeneratedValue
    private UUID testId;

    @NotNull
    private UUID patientId;

    @NotNull
    private String testDate;

    private String testName;

    private String testReport;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TestStatus status = TestStatus.PENDING;

    @NotNull
    private boolean isActive = true;

    @Generated
    @Column(name="created_at", insertable=false)
    private OffsetDateTime createdAt;

    private OffsetDateTime modifiedAt;
}
