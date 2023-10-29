package org.teamseven.hms.backend.shared.exception;

public class SampleCustomFieldUnexpectedException extends RuntimeException{
    private final String[] errorFields;
    public SampleCustomFieldUnexpectedException(String message, String[] errorFields) {
        super(message);
        this.errorFields = errorFields;
    }

    public String[] getErrorFields() {
        return errorFields;
    }
}
