package org.teamseven.hms.backend.booking.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import org.teamseven.hms.backend.catalog.entity.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE bookings SET is_active = false WHERE booking_id=?")
@Where(clause = "is_active = true")
@Table(name = "bookings")
public class Booking {
    @Id
    @Column(name="booking_id", insertable=false)
    @GeneratedValue
    private UUID bookingId;

    private UUID appointmentId;

    private UUID testId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "service_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Service service;

    @NotNull
    private UUID patientId;

    @Generated
    @Column(name="bill_number", insertable=false)
    private BigInteger billNumber;

    @Enumerated(EnumType.STRING)
    private BillStatus billStatus;

    private BigDecimal amountPaid;

    private OffsetDateTime paidAt;

    @NotNull
    private Long gst = 8L;

    @NotNull
    private String slots;

    @NotNull
    private Date reservedDate;

    @NotNull
    private boolean isActive = true;

    @Generated
    @Column(name="created_at", insertable=false)
    private OffsetDateTime createdAt;

    private OffsetDateTime modifiedAt;
}