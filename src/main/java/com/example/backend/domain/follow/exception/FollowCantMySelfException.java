package com.example.backend.domain.follow.exception;

import com.example.backend.global.error.ErrorCodeMessage;
import com.example.backend.global.error.exception.CustomException;

public class FollowCantMySelfException extends CustomException {

    public FollowCantMySelfException() {
        super(ErrorCodeMessage.FOLLOW_MYSELF_FAIL);
    }
}
