package org.teamseven.hms.backend.booking.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.teamseven.hms.backend.booking.annotation.DoctorAppointmentEndpointAccessValidated;
import org.teamseven.hms.backend.booking.dto.UpdateAppointmentRequest;
import org.teamseven.hms.backend.booking.service.AppointmentService;
import org.teamseven.hms.backend.shared.ResponseWrapper;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @DoctorAppointmentEndpointAccessValidated
    @PatchMapping("/{appointmentId}")
    public ResponseEntity updateAppointments(
            @RequestBody UpdateAppointmentRequest updateAppointmentRequest,
            @PathVariable UUID appointmentId
    ) {
        boolean isUpdateSuccesful = appointmentService.updateAppointmentDetails(
                appointmentId,
                updateAppointmentRequest
        );

        return ResponseEntity
                .status(isUpdateSuccesful? HttpStatus.NO_CONTENT : HttpStatus.NOT_MODIFIED)
                .build();
    }

    @DoctorAppointmentEndpointAccessValidated
    @GetMapping
    public ResponseEntity<ResponseWrapper> getDoctorAppointments(
            @NonNull HttpServletRequest request,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return ResponseEntity.ok(
                new ResponseWrapper.Success(
                        appointmentService.getDoctorSlots(request, page, pageSize)
                )
        );
    }
}
