package org.teamseven.hms.backend.doctor.service;

import org.springframework.stereotype.Service;
import org.teamseven.hms.backend.sample.dto.SampleResponse;
import org.teamseven.hms.backend.shared.exception.SampleCustomFieldUnexpectedException;

@Service
public class DoctorService {
    public SampleResponse getSampleResponse() {
        return new SampleResponse(1, "GET:: doctor controller");
    }

    public SampleResponse getSampleResponseNonCustomException() {
        throw new IllegalArgumentException("test exception");
    }

    public SampleResponse getSampleResponseCustomException() throws SampleCustomFieldUnexpectedException {
        throw new SampleCustomFieldUnexpectedException(
                "test custom exception",
                new String[]{"test_error_field"}
        );
    }
}
