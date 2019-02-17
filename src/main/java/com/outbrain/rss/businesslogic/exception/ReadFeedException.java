package com.outbrain.rss.businesslogic.exception;

public class ReadFeedException extends RuntimeException{
    public ReadFeedException(String message) {
        super(message);
    }

    public ReadFeedException(String message, Throwable cause) {
        super(message, cause);
    }
}
