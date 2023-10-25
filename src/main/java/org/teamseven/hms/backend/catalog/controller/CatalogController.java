package org.teamseven.hms.backend.catalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teamseven.hms.backend.catalog.entity.ServiceType;
import org.teamseven.hms.backend.catalog.service.CatalogService;
import org.teamseven.hms.backend.shared.ResponseWrapper;

@RestController
@RequestMapping("/api/v1/available-services/")
public class CatalogController {
    @Autowired private CatalogService catalogService;

    @GetMapping(value = "lab-tests")
    public ResponseEntity<ResponseWrapper> getTestServices(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return ResponseEntity
                .ok()
                .body(
                        new ResponseWrapper.Success<>(
                                catalogService.getServiceCatalog(ServiceType.TEST, page, pageSize)
                        )
                );
    }
}
