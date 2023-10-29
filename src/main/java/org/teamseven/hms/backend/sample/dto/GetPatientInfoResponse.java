package org.teamseven.hms.backend.sample.dto;

import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.UUID;

public class GetPatientInfoResponse {
    @NotNull private final UUID id;

    @NotNull private final String medicalCondition;

    @NotNull private final String phoneNumber;

    @NotNull private final String gender;

    @NotNull private final Date dateOfBirth;

    public GetPatientInfoResponse(UUID id, String medicalCondition, String phoneNumber, String gender, Date dateOfBirth) {
        this.id = id;
        this.medicalCondition = medicalCondition;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public UUID getId() {
        return id;
    }

    public String getMedicalCondition() {
        return medicalCondition;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }
}
