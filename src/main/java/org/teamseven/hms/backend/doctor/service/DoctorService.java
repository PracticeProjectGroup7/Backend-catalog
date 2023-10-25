package org.teamseven.hms.backend.doctor.service;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teamseven.hms.backend.doctor.dto.DoctorProfile;
import org.teamseven.hms.backend.sample.dto.SampleResponse;
import org.teamseven.hms.backend.shared.exception.SampleCustomFieldUnexpectedException;
import org.teamseven.hms.backend.user.entity.Doctor;
import org.teamseven.hms.backend.user.entity.DoctorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DoctorService {
    @Autowired private DoctorRepository repository;

    public List<DoctorProfile> getDoctorProfiles(List<UUID> doctorIds) {
        List<Doctor> doctors = Lists.newArrayList(repository.findAllById(doctorIds));

        return doctors.stream().map(it ->
                DoctorProfile.builder()
                        .doctorId(it.getDoctorId())
                        .specialty(it.getSpeciality())
                        .yearOfExperience(it.getYearsOfExperience().toString())
                        .build()
        ).toList();
    }
}
