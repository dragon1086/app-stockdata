package com.rocky.appstockdata.exceptions;

public class DealTrainingSourceException extends Exception {
    private String message;

    public DealTrainingSourceException(String message) {
        this.message = message;
    }
}
