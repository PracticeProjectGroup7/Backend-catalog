package org.teamseven.hms.backend.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teamseven.hms.backend.user.User;
import org.teamseven.hms.backend.user.dto.PatientProfileOverview;
import org.teamseven.hms.backend.user.entity.Patient;
import org.teamseven.hms.backend.user.entity.PatientRepository;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class PatientService {
    @Autowired
    private PatientRepository repository;


    public PatientProfileOverview getPatientProfile(UUID patientId) {
        Patient patient = repository.findById(patientId).orElseThrow(NoSuchElementException::new);
        User user = patient.getUser();
        return PatientProfileOverview.builder()
                .patientName(user.getName())
                .gender(user.getGender())
                .dateOfBirth(user.getDateOfBirth())
                .build();
    }
}
