package com.pluralis.pluralis.exception;

public class EmployeeNotFoundException extends ResourceNotFoundException {

    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
