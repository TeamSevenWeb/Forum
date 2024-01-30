package com.example.forum.exceptions;

public class InvalidReactionException extends RuntimeException {
    public InvalidReactionException(String type) {
        super(String.format("%s is not a valid reaction type.", type));
    }
}
