package org.teamseven.hms.backend.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teamseven.hms.backend.admin.dto.ModifyBookingRequest;
import org.teamseven.hms.backend.admin.service.AdminService;
import org.teamseven.hms.backend.shared.ResponseWrapper;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    @Autowired private AdminService adminService;

    @PatchMapping("/modify-booking")
    public ResponseEntity<ResponseWrapper> modifyBooking(
            @RequestBody ModifyBookingRequest modifyBookingRequest
    ) {
        return ResponseEntity.ok(new ResponseWrapper.Success<>(adminService.modifyBooking(modifyBookingRequest)));
    }
}
