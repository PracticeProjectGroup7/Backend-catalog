package org.teamseven.hms.backend.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teamseven.hms.backend.booking.annotation.LabTestStatusUpdateAccessValidated;
import org.teamseven.hms.backend.booking.dto.UpdateLabTestRequest;
import org.teamseven.hms.backend.booking.service.LabTestService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tests")
public class LabTestController {
    @Autowired private LabTestService labTestService;

    @LabTestStatusUpdateAccessValidated
    @PatchMapping("/{testId}")
    public ResponseEntity updateLabTest(
            @RequestBody UpdateLabTestRequest updateLabTestRequest,
            @PathVariable UUID testId
    ) {
        boolean isUpdateSuccesful = labTestService.updateTestStatus(
                testId,
                updateLabTestRequest.getStatus()
        );

        return ResponseEntity
                .status(isUpdateSuccesful? HttpStatus.NO_CONTENT : HttpStatus.NOT_MODIFIED)
                .build();
    }
}
