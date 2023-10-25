package org.teamseven.hms.backend.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingInfoResponse {
    private BookingType bookingType;
    private Date bookingDate;
    private String[] slots;
    private BookingDetails details;
}
