package com.example.backend.global.error;

import com.example.backend.global.error.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDTO> handleCustomException(CustomException e) {
        ErrorCodeMessage errorCodeMessage = e.getErrorCodeMessage();
        ErrorResponseDTO response = ErrorResponseDTO.of(errorCodeMessage, e.getErrors());
        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCodeMessage.getStatus()));
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDTO> handleException(Exception e) {
        ErrorResponseDTO response = ErrorResponseDTO.of(ErrorCodeMessage.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
