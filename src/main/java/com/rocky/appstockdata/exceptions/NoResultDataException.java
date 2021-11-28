package com.rocky.appstockdata.exceptions;

import lombok.Getter;

@Getter
public class NoResultDataException extends RuntimeException{
    private String message;

    public NoResultDataException(String message){
        this.message = message;
    }
}
