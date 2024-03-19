package com.example.backend.domain.user.exception;

import com.example.backend.global.error.ErrorCodeMessage;
import com.example.backend.global.error.exception.CustomException;

public class UserNotExistedException extends CustomException {

    public UserNotExistedException() {
        super(ErrorCodeMessage.USER_NOT_EXISTED);
    }
}
