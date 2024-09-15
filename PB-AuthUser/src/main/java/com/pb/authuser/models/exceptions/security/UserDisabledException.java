package com.pb.authuser.models.exceptions.security;

import com.pb.authuser.models.errors.ResponseCode;
import com.pb.authuser.models.exceptions.BaseException;

public class UserDisabledException extends BaseException {

    protected UserDisabledException(ResponseCode responseCode) {
        super(responseCode);
    }
}
