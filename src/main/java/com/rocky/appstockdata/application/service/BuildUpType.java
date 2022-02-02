package com.rocky.appstockdata.application.service;

import java.util.Arrays;
import java.util.Optional;

public enum BuildUpType {
    DAILY_CLOSING("dailyClosingPrice"),
    MINUS_CANDLE("minusCandle");

    private String buildUpType;

    BuildUpType(String buildUpType) {
        this.buildUpType = buildUpType;
    }

    public static Optional<BuildUpType> get(String buildupTypeString){
        return Arrays.stream(BuildUpType.values())
                .filter(buildUpType -> buildUpType.buildUpType.equals(buildupTypeString))
                .findFirst();
    }
}
