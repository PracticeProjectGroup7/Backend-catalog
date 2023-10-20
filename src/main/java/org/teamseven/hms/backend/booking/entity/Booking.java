package org.teamseven.hms.backend.booking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@SQLDelete(sql = "UPDATE bookings SET is_active = true WHERE id=?")
@Where(clause = "is_active = true")
@Table(name = "bookings")
public class Booking {
    @Id
    @Column(name="id", insertable=false)
    @GeneratedValue
    private UUID id;
    private UUID appointmentId;
    private UUID testId;

    @NotNull
    private UUID serviceId;

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
    private OffsetDateTime startTime;

    @NotNull
    private OffsetDateTime endTime;

    @NotNull
    private boolean isActive = true;

    @Generated()
    @Column(name="created_at", insertable=false)
    private OffsetDateTime createdAt;

    private OffsetDateTime modifiedAt;

    public UUID getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(UUID appointmentId) {
        this.appointmentId = appointmentId;
    }

    public UUID getTestId() {
        return testId;
    }

    public void setTestId(UUID testId) {
        this.testId = testId;
    }

    public UUID getServiceId() {
        return serviceId;
    }

    public void setServiceId(UUID serviceId) {
        this.serviceId = serviceId;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public BillStatus getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(BillStatus billStatus) {
        this.billStatus = billStatus;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public UUID getId() {
        return id;
    }

    public BigInteger getBillNumber() {
        return billNumber;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(OffsetDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public Long getGst() {
        return gst;
    }

    public void setGst(Long gst) {
        this.gst = gst;
    }

    public OffsetDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(OffsetDateTime startTime) {
        this.startTime = startTime;
    }

    public OffsetDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(OffsetDateTime endTime) {
        this.endTime = endTime;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public OffsetDateTime getModifiedAt() {
        return modifiedAt;
    }
}