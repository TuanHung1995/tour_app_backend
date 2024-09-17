package com.springboot.mvc.tour_app.exception;

import org.springframework.http.HttpStatus;

public class AppAPIException extends RuntimeException{

    private HttpStatus status;
    private String message;

    public AppAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}
