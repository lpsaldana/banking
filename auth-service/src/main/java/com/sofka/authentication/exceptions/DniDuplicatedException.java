package com.sofka.authentication.exceptions;

public class DniDuplicatedException extends RuntimeException{
    public DniDuplicatedException(){
        super("Persona ya registrada anteriormente");
    }
}
