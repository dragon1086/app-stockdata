package com.rocky.appstockdata.exceptions;

import lombok.Getter;

@Getter
public class NotAllowedException extends Exception {
    private String message;

    public NotAllowedException(String message) {
        this.message = message;
    }
}
