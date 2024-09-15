package com.pb.authuser.models.exceptions;

import com.pb.authuser.models.errors.ResponseCode;

public class UsernameAlreadyExistsException extends BaseException {

    public UsernameAlreadyExistsException() {
        super(ResponseCode.USERNAME_ALREADY_EXISTS);
    }
}
