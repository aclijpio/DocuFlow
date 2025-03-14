package com.github.aclijpio.docuflow.services.exceptions;

public class DocumentProcessorException extends RuntimeException{
    public DocumentProcessorException() {
    }

    public DocumentProcessorException(String message) {
        super(message);
    }

    public DocumentProcessorException(String message, Throwable cause) {
        super(message, cause);
    }
}
