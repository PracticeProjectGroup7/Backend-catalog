package org.teamseven.hms.backend.booking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@SQLDelete(sql = "UPDATE tests SET is_active = true WHERE id=?")
@Where(clause = "is_active = true")
@Table(name = "tests")
public class Test {
    @Id
    @Column(name="id", insertable=false)
    @GeneratedValue
    private UUID id;

    @NotNull
    private UUID patientId;

    @NotNull
    private Date testDate;

    @NotNull
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

    public UUID getId() {
        return id;
    }
    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestReport() {
        return testReport;
    }

    public void setTestReport(String testReport) {
        this.testReport = testReport;
    }

    public TestStatus getStatus() {
        return status;
    }

    public void setStatus(TestStatus status) {
        this.status = status;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
    public OffsetDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(OffsetDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
