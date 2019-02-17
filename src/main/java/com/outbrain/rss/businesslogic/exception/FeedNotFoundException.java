package com.outbrain.rss.businesslogic.exception;

public class FeedNotFoundException extends RuntimeException {
    public FeedNotFoundException(String message) {
        super(message);
    }

    public FeedNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
