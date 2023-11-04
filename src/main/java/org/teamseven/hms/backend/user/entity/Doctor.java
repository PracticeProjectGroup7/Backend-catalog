package org.teamseven.hms.backend.user.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import org.teamseven.hms.backend.user.User;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE doctor SET is_active = 0 WHERE servicesid=?")
@Where(clause = "is_active = 1")
@Table(name = "doctor")
public class Doctor {
    @Id
    @Column(name="doctorid", insertable=false)
    @GeneratedValue
    private UUID doctorId;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "userid")
    private User user;

    private String speciality;

    @Column(name = "consultationfees")
    private Double consultationFees;

    @Column(name = "yearsofexp")
    private Integer yearsOfExperience;

    @Column(insertable=false)
    private Integer isActive = 1;

    @Generated()
    @Column(name="created_at", insertable=false)
    private OffsetDateTime createdAt;

    private OffsetDateTime modifiedAt;
}
