package org.teamseven.hms.backend.user.service;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teamseven.hms.backend.user.User;
import org.teamseven.hms.backend.user.dto.PatientProfileOverview;
import org.teamseven.hms.backend.user.entity.Patient;
import org.teamseven.hms.backend.user.entity.PatientRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class PatientService {
    @Autowired
    private PatientRepository repository;


    public PatientProfileOverview getPatientProfile(UUID patientId) {
        Patient patient = repository.findById(patientId).orElseThrow(NoSuchElementException::new);
        User user = patient.getUser();
        return getOverview(user, patientId);
    }

    public List<PatientProfileOverview> getByUUIDs(List<UUID> patientIds) {
        List<Patient> patients = Lists.newArrayList(repository.findAllById(patientIds));

        return patients.stream().map(it -> getOverview(it.getUser(), it.getPatientId())).toList();
    }

    private PatientProfileOverview getOverview(User user, UUID patient) {
        return PatientProfileOverview.builder()
                .patientName(user.getName())
                .gender(user.getGender())
                .dateOfBirth(user.getDateOfBirth())
                .patientId(patient)
                .build();
    }
}
