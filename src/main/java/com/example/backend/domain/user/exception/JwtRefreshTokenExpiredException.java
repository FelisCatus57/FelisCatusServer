package com.example.backend.domain.user.exception;

import com.example.backend.global.error.ErrorCodeMessage;
import com.example.backend.global.error.exception.CustomException;

public class JwtRefreshTokenExpiredException extends CustomException {

    public JwtRefreshTokenExpiredException() {
        super(ErrorCodeMessage.JWT_REFRESH_TOKEN_EXPIRED);
    }
}
