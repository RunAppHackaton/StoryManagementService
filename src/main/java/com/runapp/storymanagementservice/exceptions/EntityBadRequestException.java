package com.runapp.storymanagementservice.exceptions;

public class EntityBadRequestException extends RuntimeException {
    public EntityBadRequestException(String message) {
        super(message);
    }
}
