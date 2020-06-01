package com.pere.demo.example.exceptions;

public class CompanyNotFoundException extends RuntimeException {

    public CompanyNotFoundException(Long id) {
        super("Could not find Company with Id = " + id);
    }
}