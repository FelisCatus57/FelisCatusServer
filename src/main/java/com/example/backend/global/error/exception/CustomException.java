package com.example.backend.global.error.exception;

import com.example.backend.global.error.ErrorCodeMessage;
import com.example.backend.global.error.ErrorResponseDTO;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CustomException extends RuntimeException{

    private ErrorCodeMessage errorCodeMessage;
    private List<ErrorResponseDTO.FieldError> errors = new ArrayList<>();

    public CustomException(String message, ErrorCodeMessage errorCodeMessage) {
        super(message);
        this.errorCodeMessage = errorCodeMessage;
    }

    public CustomException(ErrorCodeMessage errorCodeMessage) {
        super(errorCodeMessage.getMessage());
        this.errorCodeMessage = errorCodeMessage;
    }

    public CustomException(ErrorCodeMessage errorCodeMessage, List<ErrorResponseDTO.FieldError> errors) {
        super(errorCodeMessage.getMessage());
        this.errors = errors;
        this.errorCodeMessage = errorCodeMessage;
    }

}
