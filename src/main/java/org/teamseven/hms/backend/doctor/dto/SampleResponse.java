package org.teamseven.hms.backend.doctor.dto;

import jakarta.validation.constraints.NotNull;

public class SampleResponse {
    @NotNull private final Integer sampleInt;
    @NotNull private final String randomText;

    public SampleResponse(int randomNumber, String randomText) {
        this.sampleInt = randomNumber;
        this.randomText = randomText;
    }

    public SampleResponse() {
        this.sampleInt = 0;
        this.randomText = "";
    }

    public Integer getSampleInt() {
        return sampleInt;
    }

    public String getRandomText() {
        return randomText;
    }
}
