package com.nextgen.sgp.exception;

public class ArquivoAmazonException extends Exception {

    public ArquivoAmazonException() {}

    public ArquivoAmazonException(String message) {
        super(message);
    }

    public ArquivoAmazonException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArquivoAmazonException(Throwable cause) {
        super(cause);
    }

    public ArquivoAmazonException(Exception e) {
        super(e);
    }

}
