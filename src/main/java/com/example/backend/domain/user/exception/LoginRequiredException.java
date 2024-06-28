package com.example.backend.domain.user.exception;

import com.example.backend.global.error.ErrorCodeMessage;
import com.example.backend.global.error.exception.CustomException;

public class LoginRequiredException extends CustomException {
    public LoginRequiredException() {
        super(ErrorCodeMessage.AUTHENTICATION_FAIL);
    }
}
