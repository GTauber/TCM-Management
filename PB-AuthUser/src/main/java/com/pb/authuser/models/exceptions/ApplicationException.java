package com.pb.authuser.models.exceptions;

import com.pb.authuser.models.errors.ResponseCode;

public class ApplicationException extends BaseException {

    public ApplicationException() {
        super(ResponseCode.GENERIC_ERROR);
    }
}
