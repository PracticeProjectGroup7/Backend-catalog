package org.teamseven.hms.backend.shared.exception;

public class UnauthorizedAccessException extends RuntimeException{
    private static final String UNAUTHORIZED_ACCESS_MESSAGE = "Access to resources unauthorized";
    public UnauthorizedAccessException() {
        super(UNAUTHORIZED_ACCESS_MESSAGE);
    }
}
