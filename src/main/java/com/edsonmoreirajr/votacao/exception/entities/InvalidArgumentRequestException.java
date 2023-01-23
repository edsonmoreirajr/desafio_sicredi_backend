package com.edsonmoreirajr.votacao.exception.entities;

public class InvalidArgumentRequestException extends RuntimeException {

    public InvalidArgumentRequestException() {
    }

    public InvalidArgumentRequestException(String message) {
        super(message);
    }

    public InvalidArgumentRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidArgumentRequestException(Throwable cause) {
        super(cause);
    }

    public InvalidArgumentRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
