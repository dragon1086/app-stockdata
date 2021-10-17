package com.rocky.appstockdata.exceptions;

public class BuildUpSourceException extends Exception{
    private String message;

    public BuildUpSourceException(String message) {
        this.message = message;
    }
}
