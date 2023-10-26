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

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE fees SET is_active = false WHERE fee_id=?")
@Where(clause = "is_active = true")
@Table(name = "fees")
public class Fee {
    @Id
    @GeneratedValue
    private UUID feeId;

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
