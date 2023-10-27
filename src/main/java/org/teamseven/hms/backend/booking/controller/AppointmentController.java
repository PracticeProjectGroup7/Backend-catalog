package org.teamseven.hms.backend.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teamseven.hms.backend.booking.annotation.AppointmentUpdateAccessValidated;
import org.teamseven.hms.backend.booking.dto.UpdateAppointmentRequest;
import org.teamseven.hms.backend.booking.service.AppointmentService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/appointments/")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @AppointmentUpdateAccessValidated
    @PatchMapping("{appointmentId}")
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
}
