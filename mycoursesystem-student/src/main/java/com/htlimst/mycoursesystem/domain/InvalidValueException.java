package com.htlimst.mycoursesystem.domain;

public class InvalidValueException extends RuntimeException {
    public InvalidValueException(String message){
        super(message);
    }
}
