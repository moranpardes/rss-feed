package com.outbrain.rss.controller;

import com.outbrain.rss.businesslogic.exception.FeedNotFoundException;
import com.outbrain.rss.businesslogic.exception.InvalidFeedException;
import com.outbrain.rss.businesslogic.exception.ReadFeedException;
import com.outbrain.rss.businesslogic.exception.RssResourceLoderException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex){
        return new ResponseEntity<>(new ApiError(BAD_REQUEST, "Data integrity violation"), BAD_REQUEST);
    }

    @ExceptionHandler(FeedNotFoundException.class)
    protected ResponseEntity<Object> handleFeedNotFoundException(FeedNotFoundException ex){
        return new ResponseEntity<>(new ApiError(NOT_FOUND, ex.getMessage()), NOT_FOUND);
    }

    @ExceptionHandler(InvalidFeedException.class)
    protected ResponseEntity<Object> handleInvalidFeedException(InvalidFeedException ex){
        return new ResponseEntity<>(new ApiError(BAD_REQUEST, ex.getMessage()), BAD_REQUEST);
    }

    @ExceptionHandler(ReadFeedException.class)
    protected ResponseEntity<Object> handleReadFeedException(ReadFeedException ex){
        return new ResponseEntity<>(new ApiError(INTERNAL_SERVER_ERROR, ex.getMessage()), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RssResourceLoderException.class)
    protected ResponseEntity<Object> handleRssResourceLoderException(RssResourceLoderException ex){
        return new ResponseEntity<>(new ApiError(INTERNAL_SERVER_ERROR, ex.getMessage()), INTERNAL_SERVER_ERROR);
    }
    //

}
