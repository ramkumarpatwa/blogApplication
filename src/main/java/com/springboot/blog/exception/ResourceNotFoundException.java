package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)

public class ResourceNotFoundException extends RuntimeException{//Step1: extend it with RuntimeException
    //Step2: Define fields
    private String resourceName;
    private String fieldName;
    private long fieldValue;

    //Step3: Generate all args constructor and pass message to super class constructor
    public ResourceNotFoundException(String fieldName, String resourceName, long fieldValue) {
        super(String.format("%s not found with %s: %s", fieldName, resourceName, fieldValue));//custom message should be like POST not found with id:1
        this.fieldName = fieldName;
        this.resourceName = resourceName;
        this.fieldValue = fieldValue;
    }
    //TODO getters method
}
