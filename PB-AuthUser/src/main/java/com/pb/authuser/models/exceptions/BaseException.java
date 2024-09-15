package com.pb.authuser.models.exceptions;

import com.pb.authuser.models.errors.ResponseCode;
import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {

    private final ResponseCode responseCode;

    protected BaseException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }

    public String getErrorCode() {
        return this.getResponseCode().getCode();
    }

}
