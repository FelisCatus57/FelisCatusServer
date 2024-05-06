package com.example.backend.domain.story.exception;

import com.example.backend.global.error.ErrorCodeMessage;
import com.example.backend.global.error.exception.CustomException;

public class StoryCanNotDeleteException extends CustomException {

    public StoryCanNotDeleteException() {
        super(ErrorCodeMessage.STORY_USER_DELETE_FAIL);
    }
}
