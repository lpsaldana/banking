package com.sofka.authentication.exceptions;

public class UsernameTakenException extends RuntimeException{
    public UsernameTakenException() {
        super("Este nombre de usuario no puede ser utilizado, intente con otro valor");
    }
}
