package com.example.backend.domain.user.exception;

import com.example.backend.global.error.ErrorCodeMessage;
import com.example.backend.global.error.exception.CustomException;

public class UsernameAlreadyExistedException extends CustomException {

    public UsernameAlreadyExistedException() {
        super(ErrorCodeMessage.USERNAME_EXIST);
    }
}
