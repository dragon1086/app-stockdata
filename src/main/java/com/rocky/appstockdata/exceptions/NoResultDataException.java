package com.rocky.appstockdata.exceptions;

public class NoResultDataException extends RuntimeException{
    private String message;

    public NoResultDataException(String message){
        this.message = message;
    }
}
