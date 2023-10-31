package org.teamseven.hms.backend.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teamseven.hms.backend.admin.annotation.AdminEndpointAccessValidated;
import org.teamseven.hms.backend.admin.dto.ModifyBookingRequest;
import org.teamseven.hms.backend.admin.dto.ModifyTestRequest;
import org.teamseven.hms.backend.admin.service.AdminService;
import org.teamseven.hms.backend.bill.dto.UpdateBillRequest;
import org.teamseven.hms.backend.bill.service.BillService;
import org.teamseven.hms.backend.shared.ResponseWrapper;
import org.teamseven.hms.backend.user.UserRequest;
import org.teamseven.hms.backend.user.dto.CreateHospitalAccountRequest;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AdminController {
    @Autowired private AdminService adminService;
    @Autowired private BillService billService;

    @AdminEndpointAccessValidated
    @PatchMapping("/modify-booking")
    public ResponseEntity<ResponseWrapper> modifyBooking(
            @RequestBody ModifyBookingRequest modifyBookingRequest
    ) {
        return ResponseEntity.ok(new ResponseWrapper.Success<>(adminService.modifyBooking(modifyBookingRequest)));
    }

    @AdminEndpointAccessValidated(isReceptionistAccessAllowed = true)
    @PatchMapping("/bills/{booking_id}")
    public ResponseEntity<ResponseWrapper> updateBillStatus(
            @PathVariable String booking_id,
            @RequestBody UpdateBillRequest updateBillRequest
    ) {
        return ResponseEntity.ok(new ResponseWrapper.Success<>(billService.updateBill(updateBillRequest, booking_id)));
    }

    @AdminEndpointAccessValidated
    @PatchMapping("/modify-test")
    public ResponseEntity<ResponseWrapper> modifyTest(
            @RequestBody ModifyTestRequest modifyTestRequest
    ) {
        return ResponseEntity.ok(new ResponseWrapper.Success<>(adminService.modifyTest(modifyTestRequest)));
    }

    @AdminEndpointAccessValidated
    @PostMapping("/hospital-staff")
    public ResponseEntity<ResponseWrapper> createHospitalStaffAccount(
            @RequestBody CreateHospitalAccountRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseWrapper.Success(adminService.createStaffAccount(request)));
    }

    @AdminEndpointAccessValidated
    @GetMapping("/patients")
    public ResponseEntity<ResponseWrapper> getAllPatients(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return ResponseEntity.ok(new ResponseWrapper.Success<>(adminService.getAllPatients(page, pageSize)));
    }


    @AdminEndpointAccessValidated
    @GetMapping("/staff")
    public ResponseEntity<ResponseWrapper> getAllStaff(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return ResponseEntity.ok(new ResponseWrapper.Success<>(adminService.getAllStaff(page, pageSize)));
    }

    @AdminEndpointAccessValidated
    @GetMapping("/staff/{staff_id}")
    public ResponseEntity<ResponseWrapper> getStaffProfile(
            @PathVariable UUID staff_id
    ) {
        return ResponseEntity.ok(new ResponseWrapper.Success<>(adminService.getStaffProfile(staff_id)));
    }

    @AdminEndpointAccessValidated
    @PatchMapping("staff/{user_id}")
    public ResponseEntity<ResponseWrapper> updateStaffProfile(
            @PathVariable UUID user_id,
            @RequestBody UserRequest userRequest
    ) {
        return ResponseEntity.ok(
                new ResponseWrapper.Success<>(adminService.updateStaffProfile(userRequest, user_id))
        );
    }
}
