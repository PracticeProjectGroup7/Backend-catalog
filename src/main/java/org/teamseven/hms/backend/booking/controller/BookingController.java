package org.teamseven.hms.backend.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teamseven.hms.backend.booking.annotation.PatientBookingAccessValidated;
import org.teamseven.hms.backend.booking.service.BookingService;
import org.teamseven.hms.backend.shared.ResponseWrapper;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/services/")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PatientBookingAccessValidated
    @GetMapping(value = "booking-history/{patientId}")
    public ResponseEntity<ResponseWrapper> getBookingHistory(
            @PathVariable UUID patientId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return ResponseEntity
                .ok()
                .body(
                        new ResponseWrapper.Success<>(
                                bookingService.getBookingHistory(patientId, page, pageSize)
                        )
                );
    }


    @PatientBookingAccessValidated
    @GetMapping(value = "bookings/{bookingId}")
    public ResponseEntity<ResponseWrapper> getBookingDetails(
            @PathVariable UUID bookingId
    ) {
        return ResponseEntity
                .ok()
                .body(new ResponseWrapper.Success<>(bookingService.getBookingInfo(bookingId)));
    }
}
