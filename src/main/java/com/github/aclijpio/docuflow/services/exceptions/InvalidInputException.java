package com.github.aclijpio.docuflow.services.exceptions;

public class InvalidInputException extends Exception{
    public InvalidInputException() {
    }

    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
