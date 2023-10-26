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
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE appointments SET is_active = false WHERE appointment_id=?")
@Where(clause = "is_active = true")
@Table(name = "appointments")
public class Appointment {
    @Id
    @Column(name="appointment_id", insertable=false)
    @GeneratedValue
    private UUID appointmentId;

    @NotNull
    private UUID patientId;

    private String diagnosis;

    private String prescription;

    @NotNull
    private boolean isActive = true;

    @Column(name="created_at", insertable=false)
    private OffsetDateTime createdAt;

    private OffsetDateTime modifiedAt;
}
