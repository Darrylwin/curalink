package com.darcode.curalink.exception;

public class ResourceAlreadyExistsException extends RuntimeException {

    public ResourceAlreadyExistsException of(String resource, Integer id) {
        return new ResourceAlreadyExistsException(resource + " with id " + id + " already exists");
    }

    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
