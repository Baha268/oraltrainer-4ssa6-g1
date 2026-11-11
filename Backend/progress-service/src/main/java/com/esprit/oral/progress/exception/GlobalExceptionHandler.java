package com.esprit.oral.progress.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** RFC 7807 error responses (project convention, Annex A). */
@RestControllerAdvice
public class GlobalExceptionHandler {

    public static class NotFoundException extends RuntimeException {
        public NotFoundException(String m) { super(m); }
    }
    public static class ConflictException extends RuntimeException {
        public ConflictException(String m) { super(m); }
    }
    public static class RemoteServiceUnavailableException extends RuntimeException {
        public RemoteServiceUnavailableException(String m) { super(m); }
    }

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail notFound(NotFoundException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    public ProblemDetail conflict(ConflictException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(RemoteServiceUnavailableException.class)
    public ProblemDetail unavailable(RemoteServiceUnavailableException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
    }
}
