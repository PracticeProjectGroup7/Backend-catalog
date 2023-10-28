package org.teamseven.hms.backend.bill.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBillRequest {
    private String bookingId;
    private String billStatus;
    private String paidAt;
}
