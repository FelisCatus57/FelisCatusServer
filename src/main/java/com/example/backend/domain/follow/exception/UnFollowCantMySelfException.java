package com.example.backend.domain.follow.exception;

import com.example.backend.global.error.ErrorCodeMessage;
import com.example.backend.global.error.exception.CustomException;

public class UnFollowCantMySelfException extends CustomException {
    public UnFollowCantMySelfException() {
        super(ErrorCodeMessage.UNFOLLOW_MYSELF_FAIL);
    }
}
