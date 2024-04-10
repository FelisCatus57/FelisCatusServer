package com.example.backend.global.error.exception;

import com.example.backend.global.error.ErrorCodeMessage;

public class FileImageTypeMismatch extends CustomException {
    public FileImageTypeMismatch() {
        super(ErrorCodeMessage.FILE_IMAGE_TYPE_MISMATCH);
    }
}
