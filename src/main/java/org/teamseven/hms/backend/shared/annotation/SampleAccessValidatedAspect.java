package org.teamseven.hms.backend.shared.annotation;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.teamseven.hms.backend.shared.exception.UnauthorizedAccessException;

@Aspect
@Component
public class SampleAccessValidatedAspect {
    @Before("@annotation(org.teamseven.hms.backend.shared.annotation.SampleAccessValidated)")
    private void validateAccessAllowed(JoinPoint joinPoint) {
        HttpServletRequest request = (
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes()
        ).getRequest();
        String userIdInHeader = request.getHeader("userId");

        if (userIdInHeader == null || !userIdInHeader.equals("1")) {
            throw new UnauthorizedAccessException();
        }
    }
}
