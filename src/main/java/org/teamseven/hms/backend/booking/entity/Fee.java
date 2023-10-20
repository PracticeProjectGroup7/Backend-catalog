package org.teamseven.hms.backend.booking.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Generated;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

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

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }

    public void setType(FeeType type) {
        this.type = type;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setModifiedAt(OffsetDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getBookingId() {
        return bookingId;
    }

    public FeeType getType() {
        return type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isActive() {
        return isActive;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getModifiedAt() {
        return modifiedAt;
    }
}
