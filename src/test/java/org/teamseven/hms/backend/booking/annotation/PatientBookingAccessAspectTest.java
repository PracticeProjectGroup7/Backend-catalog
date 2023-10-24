package org.teamseven.hms.backend.booking.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.teamseven.hms.backend.booking.controller.BookingController;
import org.teamseven.hms.backend.config.JwtService;
import org.teamseven.hms.backend.shared.exception.UnauthorizedAccessException;
import org.teamseven.hms.backend.user.dto.UserWithRoleIdentifier;
import org.teamseven.hms.backend.user.service.UserService;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatientBookingAccessAspectTest {
    @Mock
    private JwtService jwtService;

    @Mock
    private UserService userService;

    @InjectMocks
    PatientBookingAccessAspect aspect;

    @BeforeEach
    public void setUp() {
        Mockito.reset(jwtService, userService);
    }

    @Test
    public void testValidateAccessAllowed_jwtTokenMissing_throwsUnauthorizedRequest() {
        ProceedingJoinPoint proceedingJoinPoint = mock();
        PatientBookingAccessValidated patientBookingAccessValidated = mock();
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        assertThrows(
                UnauthorizedAccessException.class,
                () -> { aspect.validateAccessAllowed(proceedingJoinPoint, patientBookingAccessValidated); }
        );
    }

    @Test
    public void testValidateAccessAllowed_userIsDoctor_assertNotThrowUnauthorized() {
        ProceedingJoinPoint proceedingJoinPoint = mock();
        PatientBookingAccessValidated patientBookingAccessValidated = mock();
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        request.addHeader("Authorization", "Bearer mock token");

        when(jwtService.extractUsername(any())).thenReturn("mock.mail@domain.org");
        when(userService.getUserWithRoleIdentifier(any())).thenReturn(
                new UserWithRoleIdentifier.Doctor(UUID.fromString("166b8eb1-5ea1-408a-b0d2-fe4ee88f57eb"))
        );

        assertDoesNotThrow(
                () -> { aspect.validateAccessAllowed(proceedingJoinPoint, patientBookingAccessValidated); }
        );
    }

    @Test
    public void testValidateAccessAllowed_userIsPatient_accessingByPatientId_whenAuthorizedThenSuccess() throws Exception {
        ProceedingJoinPoint proceedingJoinPoint = mock();
        JoinPoint.StaticPart staticPartJoinPoint = mock();
        MethodSignature methodSignature = mock();
        PatientBookingAccessValidated patientBookingAccessValidated = mock();

        mockPatientAccessSetUp(patientBookingAccessValidated, methodSignature, staticPartJoinPoint);

        when(proceedingJoinPoint.getStaticPart()).thenReturn(staticPartJoinPoint);
        when(proceedingJoinPoint.getArgs()).thenReturn(new Object[]{"166b8eb1-5ea1-408a-b0d2-fe4ee88f57eb", 1, 10});

        assertDoesNotThrow(
                () -> { aspect.validateAccessAllowed(proceedingJoinPoint, patientBookingAccessValidated); }
        );
    }

    @Test
    public void testValidateAccessAllowed_userIsPatient_accessingByPatientId_whenunauthorizedThenThrowException()
            throws Exception {
        ProceedingJoinPoint proceedingJoinPoint = mock();
        JoinPoint.StaticPart staticPartJoinPoint = mock();
        MethodSignature methodSignature = mock();
        PatientBookingAccessValidated patientBookingAccessValidated = mock();

        mockPatientAccessSetUp(patientBookingAccessValidated, methodSignature, staticPartJoinPoint);

        when(proceedingJoinPoint.getStaticPart()).thenReturn(staticPartJoinPoint);
        when(proceedingJoinPoint.getArgs()).thenReturn(new Object[]{"ae3f7eaf-185e-4487-b9a0-0001b6ac057f", 1, 10});

        assertThrows(
                UnauthorizedAccessException.class,
                () -> aspect.validateAccessAllowed(proceedingJoinPoint, patientBookingAccessValidated)
        );
    }

    private void mockPatientAccessSetUp(
            PatientBookingAccessValidated patientBookingAccessValidated,
            MethodSignature methodSignature,
            JoinPoint.StaticPart staticPartJoinPoint
    ) throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        request.addHeader("Authorization", "Bearer mock token");

        when(jwtService.extractUsername(any())).thenReturn("mock.mail@domain.org");
        when(userService.getUserWithRoleIdentifier(any())).thenReturn(
                new UserWithRoleIdentifier.Patient(UUID.fromString("166b8eb1-5ea1-408a-b0d2-fe4ee88f57eb"))
        );
        when(patientBookingAccessValidated.dataRequestMethod()).thenReturn(PatientDataRequestedMethod.PATIENT_ID_PATH);
        when(methodSignature.getMethod()).thenReturn(
                BookingController.class.getMethod("getBookingHistory", UUID.class, int.class, int.class)
        );

        when(staticPartJoinPoint.getSignature()).thenReturn(methodSignature);
    }
}
