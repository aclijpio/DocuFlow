package com.github.aclijpio.docuflow.services.exceptions;

public class InvalidDocumentFormatException extends RuntimeException {
    public InvalidDocumentFormatException() {
    }

    public InvalidDocumentFormatException(String message) {
        super(message);
    }

    public InvalidDocumentFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
