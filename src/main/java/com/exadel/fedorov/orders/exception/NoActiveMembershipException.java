package com.exadel.fedorov.orders.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoActiveMembershipException extends Exception {

    public NoActiveMembershipException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoActiveMembershipException(String message) {
        super(message);
    }

}