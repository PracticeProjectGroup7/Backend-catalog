package org.teamseven.hms.backend.sample.service;

import org.springframework.stereotype.Service;
import org.teamseven.hms.backend.sample.dto.SampleResponse;
import org.teamseven.hms.backend.shared.exception.SampleCustomFieldUnexpectedException;

@Service
public class SampleService {
    public SampleResponse getSampleResponse() {
        return new SampleResponse(1, "test");
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
