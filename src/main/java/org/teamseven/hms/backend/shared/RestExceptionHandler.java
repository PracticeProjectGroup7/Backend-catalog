package org.teamseven.hms.backend.shared;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.teamseven.hms.backend.shared.exception.ResourceNotFoundException;
import org.teamseven.hms.backend.shared.exception.SampleCustomFieldUnexpectedException;
import org.teamseven.hms.backend.shared.exception.UnauthorizedAccessException;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseWrapper> handleIllegalArgumentExc(
            IllegalArgumentException exception
    ) {
        return ResponseEntity.badRequest().body(
                new ResponseWrapper.BadRequest(exception.getMessage(), null)
        );
    }

    /**
     * handleSampleCustomFieldErrorExc - catch custom exception
     * @param exception customer-defined SampleCustomFieldUnexpectedException
     * @return Response with status code 400
     */
    @ExceptionHandler(SampleCustomFieldUnexpectedException.class)
    public ResponseEntity<ResponseWrapper> handleSampleCustomFieldErrorExc(
            SampleCustomFieldUnexpectedException exception
    ) {
        return ResponseEntity.badRequest().body(
                new ResponseWrapper.BadRequest(
                        exception.getMessage(),
                        exception.getErrorFields()
                )
        );
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ResponseWrapper> handleUnauthorizedExc(
            UnauthorizedAccessException exception
    ) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ResponseWrapper.GenericError(exception.getMessage())
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseWrapper> handleNotFoundExc(
            ResourceNotFoundException exception
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseWrapper.GenericError(exception.getMessage())
        );
    }

    /**
     * handleExceptionWithoutSpecificHandler
     * @param exception any exception without specified handler
     * @return Response with status 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseWrapper> handleExceptionWithoutSpecificHandler(
            Exception exception
    ) {
        return ResponseEntity.internalServerError().body(
                new ResponseWrapper.GenericError(exception.getMessage())
        );
    }
}
