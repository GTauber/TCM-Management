package com.pb.authuser.models.exceptions;

import com.pb.authuser.models.errors.ResponseCode;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException() {
        super(ResponseCode.USER_NOT_FOUND);
    }
}
