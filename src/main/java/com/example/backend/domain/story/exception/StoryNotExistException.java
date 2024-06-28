package com.example.backend.domain.story.exception;

import com.example.backend.global.error.ErrorCodeMessage;
import com.example.backend.global.error.exception.CustomException;

public class StoryNotExistException extends CustomException {

    public StoryNotExistException() {
        super(ErrorCodeMessage.STORY_NOT_FOUND);
    }
}
