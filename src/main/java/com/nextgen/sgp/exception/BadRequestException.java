package com.nextgen.sgp.exception;

import jakarta.validation.ValidationException;

public class BadRequestException extends ValidationException {

    public BadRequestException() {}

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }

    public BadRequestException(Exception e) {
        super(e);
    }

}
