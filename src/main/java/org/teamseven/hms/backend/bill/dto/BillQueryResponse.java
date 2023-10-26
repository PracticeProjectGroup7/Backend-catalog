package org.teamseven.hms.backend.bill.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.teamseven.hms.backend.booking.entity.BillStatus;
import org.teamseven.hms.backend.booking.entity.Fee;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BillQueryResponse {
    private UUID bookingId;
    private Number billNumber;
    private BillStatus billStatus;
    private BigDecimal amountPaid;
    private OffsetDateTime paidAt;
    private List<Fee> fees;
}
