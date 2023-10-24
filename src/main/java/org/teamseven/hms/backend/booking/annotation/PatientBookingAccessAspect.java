package org.teamseven.hms.backend.booking.annotation;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.assertj.core.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.teamseven.hms.backend.config.JwtService;
import org.teamseven.hms.backend.shared.exception.UnauthorizedAccessException;
import org.teamseven.hms.backend.user.dto.UserWithRoleIdentifier;
import org.teamseven.hms.backend.user.service.UserService;

@Aspect
@Component
public class PatientBookingAccessAspect {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @VisibleForTesting
    @Around("@annotation(patientBookingAccessValidated)")
    protected Object validateAccessAllowed(ProceedingJoinPoint pjp, PatientBookingAccessValidated patientBookingAccessValidated) throws Throwable{
        HttpServletRequest request = (
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()
        ).getRequest();
        String jwtToken = getJwtToken(request);
        String email = jwtService.extractUsername(jwtToken);

        // TODO: replace with new jwt definition
        UserWithRoleIdentifier user = userService.getUserWithRoleIdentifier(email);

        switch (user.role) {
            case PATIENT -> {
                PatientDataRequestedMethod dataRequestMethod = patientBookingAccessValidated.dataRequestMethod();
                BookingEndpointPatientAccessValidator validator = BookingEndpointPatientAccessValidator
                        .getValidator(dataRequestMethod);

                if (!validator.isAccessGranted(pjp, user.roleIdentifier)) {
                    throw new UnauthorizedAccessException();
                }

                return pjp.proceed();
            }
            case STAFF -> throw new UnauthorizedAccessException();
            default -> {
                return pjp.proceed();
            }
        }
    }

    private String getJwtToken(HttpServletRequest request) {
        try {
            return request.getHeader("Authorization").substring(7);
        } catch (Exception e) {
            throw new UnauthorizedAccessException();
        }
    }
}
