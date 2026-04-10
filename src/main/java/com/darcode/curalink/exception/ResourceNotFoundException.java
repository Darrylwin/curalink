package com.darcode.curalink.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, Integer id) {
        super(resource + " with id " + id + " not found");
    }

    public ResourceNotFoundException (String message) {
        super(message);
    }
}
