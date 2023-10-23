package org.teamseven.hms.backend.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teamseven.hms.backend.booking.service.BookingService;
import org.teamseven.hms.backend.shared.ResponseWrapper;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/services/")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    // TODO: add authorization check based on role for this endpoint
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
}
