package com.example.backend.domain.user.exception;

import com.example.backend.global.error.ErrorCodeMessage;
import com.example.backend.global.error.exception.CustomException;

public class UserCantSearchException extends CustomException {
    public UserCantSearchException() {
        super(ErrorCodeMessage.USER_SEARCH_FAIL);
    }
}
