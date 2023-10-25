package org.teamseven.hms.backend.booking.annotation;

import java.util.Objects;
import java.util.UUID;

// Validates the logged in patient is authorized to view booking by the patient ID param,
// i.e. the patient ID on the param is their own.
public class PatientIdParamBookingPatientAccessValidator extends BookingEndpointPatientAccessValidator {
    protected PatientIdParamBookingPatientAccessValidator() {
        super();
        this.method = PatientDataRequestedMethod.PATIENT_ID_PATH;
    }
    @Override
    protected boolean isLoggedInPatientAuthorized(Object requestedResourceId, UUID loginUserId) {
        return Objects.equals(loginUserId, UUID.fromString(requestedResourceId.toString()));
    }
}
