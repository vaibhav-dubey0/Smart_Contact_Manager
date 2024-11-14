package com.smcomanager.Helper;

public class ResorceNotFoundException extends RuntimeException {

    public ResorceNotFoundException(String message) {
        super(message);
    }

    public ResorceNotFoundException() {
        super("Resorce Not Found ");
    }
    
}
