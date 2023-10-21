package org.teamseven.hms.backend.booking.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.Generated;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@Entity
@Table(name = "fees")
public class Fee {
    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    private UUID bookingId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private FeeType type;

    @NotNull
    private BigDecimal price;

    @NotNull
    private boolean isActive;

    @Generated
    @Column(name="created_at", insertable=false)
    private OffsetDateTime createdAt;

    private OffsetDateTime modifiedAt;
}
