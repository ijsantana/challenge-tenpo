package com.tenpo.challenge.exception;

public class ExternalClientException extends RuntimeException{

    public ExternalClientException(){}

    public ExternalClientException(String message) {
        super(message);
    }

}
