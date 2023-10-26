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
}