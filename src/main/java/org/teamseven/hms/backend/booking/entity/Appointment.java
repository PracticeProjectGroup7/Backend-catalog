package org.teamseven.hms.backend.booking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
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
}
