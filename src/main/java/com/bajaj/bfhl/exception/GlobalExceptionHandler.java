package com.bajaj.bfhl.exception;

import com.bajaj.bfhl.dto.BfhlResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation errors (e.g. @NotNull, @NotEmpty violations).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BfhlResponse> handleValidationException(MethodArgumentNotValidException ex) {
        BfhlResponse errorResponse = buildErrorResponse();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Handles custom InvalidInputException.
     */
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<BfhlResponse> handleInvalidInputException(InvalidInputException ex) {
        BfhlResponse errorResponse = buildErrorResponse();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Catch-all handler for any unexpected exceptions.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BfhlResponse> handleGenericException(Exception ex) {
        BfhlResponse errorResponse = buildErrorResponse();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    private BfhlResponse buildErrorResponse() {
        return BfhlResponse.builder()
                .isSuccess(false)
                .userId("")
                .email("")
                .rollNumber("")
                .oddNumbers(Collections.emptyList())
                .evenNumbers(Collections.emptyList())
                .alphabets(Collections.emptyList())
                .specialCharacters(Collections.emptyList())
                .sum("0")
                .concatString("")
                .build();
    }
}
