package com.example.backend.domain.feed.exception;

import com.example.backend.global.error.ErrorCodeMessage;
import com.example.backend.global.error.exception.CustomException;

public class PostCantUpdateException extends CustomException {
    public PostCantUpdateException() {
        super(ErrorCodeMessage.POST_USER_UPDATE_FAIL);
    }
}
