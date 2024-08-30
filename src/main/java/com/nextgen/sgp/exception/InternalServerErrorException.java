package com.nextgen.sgp.exception;

public class InternalServerErrorException extends Exception {

    public InternalServerErrorException() {}

    public InternalServerErrorException(String message) {
        super(message);
    }

    public InternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalServerErrorException(Throwable cause) {
        super(cause);
    }

    public InternalServerErrorException(Exception e) {
        super(e);
    }

}
