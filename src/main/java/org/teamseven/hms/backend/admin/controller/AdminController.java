package org.teamseven.hms.backend.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teamseven.hms.backend.admin.dto.ModifyBookingRequest;
import org.teamseven.hms.backend.admin.dto.ModifyTestRequest;
import org.teamseven.hms.backend.admin.service.AdminService;
import org.teamseven.hms.backend.bill.dto.UpdateBillRequest;
import org.teamseven.hms.backend.bill.service.BillService;
import org.teamseven.hms.backend.shared.ResponseWrapper;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    @Autowired private AdminService adminService;
    @Autowired private BillService billService;

    @PatchMapping("/modify-booking")
    public ResponseEntity<ResponseWrapper> modifyBooking(
            @RequestBody ModifyBookingRequest modifyBookingRequest
    ) {
        return ResponseEntity.ok(new ResponseWrapper.Success<>(adminService.modifyBooking(modifyBookingRequest)));
    }

    @GetMapping("/bills")
    public ResponseEntity<ResponseWrapper> getBillByBookingId(
            @PathVariable String booking_id
    ) {
        return ResponseEntity.ok(new ResponseWrapper.Success<>(billService.getBillByBookingId(booking_id)));
    }

    @PatchMapping("/bills")
    public ResponseEntity<ResponseWrapper> updateBillStatus(
            @RequestBody UpdateBillRequest updateBillRequest
    ) {
        return ResponseEntity.ok(new ResponseWrapper.Success<>(billService.updateBill(updateBillRequest)));
    }

    @PatchMapping("/modify-test")
    public ResponseEntity<ResponseWrapper> modifyTest(
            @RequestBody ModifyTestRequest modifyTestRequest
    ) {
        return ResponseEntity.ok(new ResponseWrapper.Success<>(adminService.modifyTest(modifyTestRequest)));
    }

    @GetMapping("/patients")
    public ResponseEntity<ResponseWrapper> getAllPatients(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return ResponseEntity.ok(new ResponseWrapper.Success<>(adminService.getAllPatients(page, pageSize)));
    }
}
