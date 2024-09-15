package com.pb.authuser.models.exceptions;

import com.pb.authuser.models.errors.ResponseCode;

public class EmailAlreadyExistsException extends BaseException {
    public EmailAlreadyExistsException() {
        super(ResponseCode.EMAIL_ALREADY_EXISTS);
    }
}
