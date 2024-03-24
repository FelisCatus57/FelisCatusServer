package com.example.backend.domain.feed.exception;

import com.example.backend.global.error.ErrorCodeMessage;
import com.example.backend.global.error.exception.CustomException;

public class PostNotExistedException extends CustomException {

    public PostNotExistedException() {
        super(ErrorCodeMessage.POST_NOT_FOUND);
    }
}
