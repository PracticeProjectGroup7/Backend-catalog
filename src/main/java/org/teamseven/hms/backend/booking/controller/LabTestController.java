package org.teamseven.hms.backend.booking.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teamseven.hms.backend.booking.annotation.LabTestEndpointAccessValidated;
import org.teamseven.hms.backend.booking.dto.UpdateLabTestRequest;
import org.teamseven.hms.backend.booking.service.LabTestService;
import org.teamseven.hms.backend.shared.ResponseWrapper;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tests")
public class LabTestController {
    @Autowired private LabTestService labTestService;

    @LabTestEndpointAccessValidated
    @PatchMapping("/{testId}")
    public ResponseEntity updateLabTest(
            @Valid @RequestBody UpdateLabTestRequest updateLabTestRequest,
            @PathVariable UUID testId
    ) {
        boolean isUpdateSuccesful = labTestService.updateTestStatus(
                testId,
                updateLabTestRequest.getResult(),
                updateLabTestRequest.getStatus()
        );

        return ResponseEntity
                .status(isUpdateSuccesful? HttpStatus.NO_CONTENT : HttpStatus.NOT_MODIFIED)
                .build();
    }


    @LabTestEndpointAccessValidated
    @GetMapping
    public ResponseEntity<ResponseWrapper> getTestAppointments(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return ResponseEntity.ok(
                new ResponseWrapper.Success(
                        labTestService.getTestAppointments(page, pageSize)
                )
        );
    }
}
