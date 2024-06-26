package com.example.backend.domain.story.exception;

import com.example.backend.global.error.ErrorCodeMessage;
import com.example.backend.global.error.exception.CustomException;

public class UserStoryNotExistedException extends CustomException {

    public UserStoryNotExistedException() {
        super(ErrorCodeMessage.STORY_USER_NOT_EXISTED);
    }
}
