package com.example.AISafePSOFT_26.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        return buildError(
                HttpStatus.NOT_FOUND,
                "Not Found",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
    }

    // 400 validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleValidationErrors(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.toList());

        return buildError(
                HttpStatus.BAD_REQUEST,
                "Validation Failed",
                "Invalid input data",
                request.getRequestURI(),
                errors
        );
    }

    // 400 malformed JSON
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDetails> handleJsonError(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        return buildError(
                HttpStatus.BAD_REQUEST,
                "Malformed JSON",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
    }

    // 409 domain errors
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorDetails> handleDomainException(
            DomainException ex,
            HttpServletRequest request) {

        return buildError(
                HttpStatus.CONFLICT,
                "Domain Error",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
    }

    // 409 database constraint errors
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDetails> handleDBError(
            DataIntegrityViolationException ex,
            HttpServletRequest request) {

        log.error("Database error at {}", request.getRequestURI(), ex);

        return buildError(
                HttpStatus.CONFLICT,
                "Database Constraint Violation",
                ex.getMostSpecificCause().getMessage(),
                request.getRequestURI(),
                null
        );
    }

    // 500 fallback
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(
            Exception ex,
            HttpServletRequest request) {

        log.error("Unexpected error at {}", request.getRequestURI(), ex);

        return buildError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
    }

    // helper
    private ResponseEntity<ErrorDetails> buildError(
            HttpStatus status,
            String error,
            String message,
            String path,
            List<String> validationErrors) {

        ErrorDetails body = new ErrorDetails(
                LocalDateTime.now(),
                status.value(),
                error,
                message,
                path,
                validationErrors
        );

        return new ResponseEntity<>(body, status);
    }
}