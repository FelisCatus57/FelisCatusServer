package com.example.backend.domain.user.exception;

import com.example.backend.global.error.ErrorCodeMessage;
import com.example.backend.global.error.exception.CustomException;

public class JwtTokenInvalidException extends CustomException {

    public JwtTokenInvalidException() {
        super(ErrorCodeMessage.JWT_TOKEN_INVALID);
    }
}
