package com.example.backend.domain.user.exception;

import com.example.backend.global.error.ErrorCodeMessage;
import com.example.backend.global.error.exception.CustomException;

public class NicknameAlreadyExistedException extends CustomException {

    public NicknameAlreadyExistedException() {
        super(ErrorCodeMessage.NICKNAME_EXISTED);
    }
}
