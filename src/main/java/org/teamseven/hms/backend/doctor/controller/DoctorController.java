package org.teamseven.hms.backend.doctor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.teamseven.hms.backend.doctor.service.DoctorService;
import org.teamseven.hms.backend.shared.ResponseWrapper;
import org.teamseven.hms.backend.shared.annotation.SampleAccessValidated;

@RestController
@RequestMapping("/api/v1/doctor")
public class DoctorController {
    @Autowired DoctorService doctorService;

    /**
     * Success response sample
     */
    @GetMapping
    public ResponseEntity<ResponseWrapper> getSampleResponse() {
        return ResponseEntity.ok(
                new ResponseWrapper.Success<>(doctorService.getSampleResponse())
        );
    }

    /**
     * Endpoint throwing Java exception
     */
    @GetMapping("/built-in-exceptions")
    public ResponseEntity<ResponseWrapper> getSampleBuiltInException() {
        return ResponseEntity.ok(
                new ResponseWrapper.Success<>(doctorService.getSampleResponseNonCustomException())
        );
    }

    /**
     * Endpoint throwing custom exception
     */
    @GetMapping("/custom-exceptions")
    public ResponseEntity<ResponseWrapper> getSampleCustomException() {
        return ResponseEntity.ok(
                new ResponseWrapper.Success<>(doctorService.getSampleResponseCustomException())
        );
    }

    /**
     * Endpoint with custom validation/annotation check
     */
    @SampleAccessValidated
    @GetMapping("/access-validations")
    public ResponseEntity<ResponseWrapper> getWithValidatedAccess() {
        return ResponseEntity.ok(
                new ResponseWrapper.Success<>(doctorService.getSampleResponse())
        );
    }
}