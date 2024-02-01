package com.employeemanagement.example.exception;

import lombok.Getter;

@Getter
public class EntityAlreadyExistsException extends RuntimeException {
    private final String entity;

    public EntityAlreadyExistsException(String entity) {
        super(entity + "already exists");
        this.entity = entity;
    }
}
