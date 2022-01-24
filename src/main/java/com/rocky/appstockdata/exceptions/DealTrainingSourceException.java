package com.rocky.appstockdata.exceptions;

import lombok.Getter;

@Getter
public class DealTrainingSourceException extends Exception {
    private String message;

    public DealTrainingSourceException(String message) {
        this.message = message;
    }
}
