package com.textprocessor.exception;

public class TextProcessingException extends Exception {

    public TextProcessingException(String message) {
        super(message);
    }

    public TextProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}