package com.rocky.appstockdata.exceptions;

import lombok.Getter;

@Getter
public class BuildUpSourceException extends Exception{
    private String message;

    public BuildUpSourceException(String message) {
        this.message = message;
    }
}
