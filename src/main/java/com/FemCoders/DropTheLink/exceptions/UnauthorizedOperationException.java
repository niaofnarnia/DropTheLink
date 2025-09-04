package com.FemCoders.DropTheLink.exceptions;

public class UnauthorizedOperationException  extends RuntimeException {
    public UnauthorizedOperationException(String message) {
        super(message);
    }
}