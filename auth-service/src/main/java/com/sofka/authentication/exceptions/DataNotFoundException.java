package com.sofka.authentication.exceptions;

public class DataNotFoundException extends RuntimeException{
    public DataNotFoundException(Long id) {
        super("No se logró encontrar el registro con id " + id);
    }
}
