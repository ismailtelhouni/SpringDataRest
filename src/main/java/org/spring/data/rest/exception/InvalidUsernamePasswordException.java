package org.spring.data.rest.exception;

public class InvalidUsernamePasswordException extends RuntimeException {
    public InvalidUsernamePasswordException(String message) {
        super(message);
    }
    public InvalidUsernamePasswordException() {}
}
