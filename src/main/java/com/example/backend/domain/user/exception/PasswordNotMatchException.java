package com.example.backend.domain.user.exception;

import com.example.backend.global.error.ErrorCodeMessage;
import com.example.backend.global.error.exception.CustomException;

public class PasswordNotMatchException extends CustomException {
    public PasswordNotMatchException() {
        super(ErrorCodeMessage.PASSWORD_NOT_MATCH);
    }
}
