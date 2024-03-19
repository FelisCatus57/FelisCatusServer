package com.example.backend.global.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponseDTO {

    private int status;
    private String message;
    private List<FieldError> errors;

    private ErrorResponseDTO(ErrorCodeMessage errorCodeMessage, List<FieldError> errors) {
        this.status = errorCodeMessage.getStatus();
        this.message = errorCodeMessage.getMessage();
        this.errors = errors;
    }

    private ErrorResponseDTO(ErrorCodeMessage errorCodeMessage) {
        this.status = errorCodeMessage.getStatus();
        this.message = errorCodeMessage.getMessage();
        this.errors = new ArrayList<>();
    }

    public static ErrorResponseDTO of(ErrorCodeMessage errorCodeMessage, BindingResult bindingResult) {
        return new ErrorResponseDTO(errorCodeMessage, FieldError.of(bindingResult));
    }

    public static ErrorResponseDTO of(ErrorCodeMessage errorCode) {
        return new ErrorResponseDTO(errorCode);
    }

    public static ErrorResponseDTO of(ErrorCodeMessage errorCode, List<FieldError> errors) {
        return new ErrorResponseDTO(errorCode, errors);
    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {

        private String field;
        private String rejectedValue;
        private String defaultMessage;

        public FieldError(String field, String rejectedValue, String defaultMessage) {
            this.field = field;
            this.rejectedValue = rejectedValue;
            this.defaultMessage = defaultMessage;
        }

        public static List<FieldError> of(String field, String rejectedValue, String defaultMessage) {
            final List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(field, rejectedValue, defaultMessage));
            return fieldErrors;
        }

        private static List<FieldError> of(BindingResult bindingResult) {
            List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }

    }
}
