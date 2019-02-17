package com.outbrain.rss.businesslogic.exception;

public class RssResourceLoderException extends RuntimeException {
    public RssResourceLoderException(String message) {
        super(message);
    }

    public RssResourceLoderException(String message, Throwable cause) {
        super(message, cause);
    }
}
