package com.nextgen.sgp.exception;

public class ConverterException extends Exception {

    public ConverterException() {}

    public ConverterException(String message) {
        super(message);
    }

    public ConverterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConverterException(Throwable cause) {
        super(cause);
    }

    public ConverterException(Exception e) {
        super(e);
    }

}
