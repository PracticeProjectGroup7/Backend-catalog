package org.teamseven.hms.backend.booking.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.UUID;

@Component
// Template method pattern
// concrete classes:
// BookingIdParamBookingPatientAccessValidator
// PatientIdParamBookingPatientAccessValidator
// both implement isLoggedInPatientAuthorized. only isAccessGranted is public
abstract class BookingEndpointPatientAccessValidator {
    protected PatientDataRequestedMethod method;

    static BookingEndpointPatientAccessValidator getValidator(PatientDataRequestedMethod method) {
        return switch (method) {
            case BOOKING_ID_PATH -> new BookingIdParamBookingPatientAccessValidator();
            case PATIENT_ID_PATH -> new PatientIdParamBookingPatientAccessValidator();
        };
    }

    public boolean isAccessGranted(
            ProceedingJoinPoint pjp,
            UUID loggedInPatient
    ) {
        String expectedParamName = this.method.getVariableName();
        String expectedParamValue = null;

        Object[] args = pjp.getArgs();
        MethodSignature methodSignature = (MethodSignature) pjp.getStaticPart().getSignature();
        Method method = methodSignature.getMethod();
        Parameter[] params = method.getParameters();
        for (int i = 0; i < params.length; i++) {
            try {
                Annotation pathVariable = params[i].getAnnotation(PathVariable.class);
                if (pathVariable == null) continue;
                if (params[i].getName().equals(expectedParamName)) {
                    expectedParamValue = args[i].toString();
                    break;
                }
            } catch (Exception e) {
                //do nothing
            }
        }

        // param not found
        if (expectedParamValue == null) {
            throw new IllegalStateException("Unable to verify request. Please try again later");
        }

        return isLoggedInPatientAuthorized(expectedParamValue, loggedInPatient);
    }

    protected abstract boolean isLoggedInPatientAuthorized(Object requestedResourceId, UUID loginUserId);
}
