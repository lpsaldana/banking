package com.sofka.authentication.exceptions;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(){
        super("Las credenciales ingresadas no son correctas");
    }
}
