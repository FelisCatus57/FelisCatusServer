package com.example.backend.domain.user.exception;

import com.example.backend.global.error.ErrorCodeMessage;
import com.example.backend.global.error.exception.CustomException;

public class JwtExpiredTokenException extends CustomException {
    public JwtExpiredTokenException() {
        super(ErrorCodeMessage.JWT_TOKEN_EXPIRED);
    }
}
