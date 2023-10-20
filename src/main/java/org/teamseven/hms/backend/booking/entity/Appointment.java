package org.teamseven.hms.backend.booking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@SQLDelete(sql = "UPDATE appointments SET is_active = true WHERE id=?")
@Where(clause = "is_active = true")
@Table(name = "appointments")
public class Appointment {
    @Id
    @Column(name="id", insertable=false)
    @GeneratedValue
    private UUID id;

    @NotNull
    private UUID patientId;

    private String diagnosis;

    private String prescription;

    @NotNull
    private boolean isActive = true;

    @Generated()
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

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
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
