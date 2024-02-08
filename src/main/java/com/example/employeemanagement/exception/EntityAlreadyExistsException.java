package com.example.employeemanagement.exception;

import lombok.Getter;

@Getter
public class EntityAlreadyExistsException extends RuntimeException {
    private final String entity;

    public EntityAlreadyExistsException(String entity) {
        super(entity + " Already exists");
        this.entity = entity;
    }
}
