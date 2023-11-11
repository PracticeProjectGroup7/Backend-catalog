package org.teamseven.hms.backend.catalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teamseven.hms.backend.catalog.dto.CreateDoctorService;
import org.teamseven.hms.backend.catalog.entity.ServiceType;
import org.teamseven.hms.backend.catalog.service.CatalogService;
import org.teamseven.hms.backend.shared.ResponseWrapper;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/available-services")
public class CatalogController {
    @Autowired private CatalogService catalogService;

    @GetMapping(value = "/lab-tests")
    public ResponseEntity<ResponseWrapper> getTestServices(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) throws IOException {
        return ResponseEntity
                .ok()
                .body(
                        new ResponseWrapper.Success<>(
                                catalogService.getServiceCatalog(ServiceType.TEST, page, pageSize)
                        )
                );
    }

    @GetMapping(value = "/doctors")
    public ResponseEntity<ResponseWrapper> getDoctorAppointmentServices(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) throws IOException {
        return ResponseEntity
                .ok()
                .body(
                        new ResponseWrapper.Success<>(
                                catalogService.getServiceCatalog(ServiceType.APPOINTMENT, page, pageSize)
                        )
                );
    }

    @GetMapping(value = "/doctors/{doctorId}")
    public ResponseEntity<ResponseWrapper> getServiceOverviewByDoctorId(
            @PathVariable UUID doctorId
    ) {
        return ResponseEntity.ok()
                .body(
                        new ResponseWrapper.Success<>(
                                catalogService.getServiceOverviewByDoctorId(doctorId)
                        )
                );
    }

    @GetMapping(value = "/{serviceId}")
    public ResponseEntity<ResponseWrapper> getServiceOverview(
            @PathVariable UUID serviceId
    ) {
        return ResponseEntity.ok()
                .body(new ResponseWrapper.Success<>(catalogService.getServiceOverview(serviceId)));
    }

    @PostMapping(value = "/doctors")
    public ResponseEntity<ResponseWrapper> createDoctorService(
            @RequestBody CreateDoctorService doctorService
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new ResponseWrapper.Success<>(
                                catalogService.createNewService(doctorService)
                        )
                );
    }
}
