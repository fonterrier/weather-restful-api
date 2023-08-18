package com.example.weather.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HttpResponseException extends Exception {
    private int responseCode;

    public HttpResponseException(String message, int responseCode) {
        super(message);
        this.responseCode = responseCode;
    }
}