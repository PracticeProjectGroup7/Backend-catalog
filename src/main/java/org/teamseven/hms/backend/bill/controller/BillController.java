package org.teamseven.hms.backend.bill.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teamseven.hms.backend.bill.service.BillService;
import org.teamseven.hms.backend.shared.ResponseWrapper;

@RestController
@RequestMapping("/api/v1/bills")
@RequiredArgsConstructor
public class BillController {
    @Autowired private BillService billService;

    @GetMapping("/{booking_id}")
    public ResponseEntity<ResponseWrapper> register(
            @PathVariable String booking_id
            ) {
        return ResponseEntity.ok(new ResponseWrapper.Success<>(billService.getBillByBookingId(booking_id)));
    }
}
