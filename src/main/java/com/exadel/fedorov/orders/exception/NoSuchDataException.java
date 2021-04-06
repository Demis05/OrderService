package com.exadel.fedorov.orders.exception;

public class NoSuchDataException extends Exception {

    public NoSuchDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchDataException(String message) {
        super(message);
    }

}