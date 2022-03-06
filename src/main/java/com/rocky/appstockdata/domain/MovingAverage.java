package com.rocky.appstockdata.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class MovingAverage {
    Map<String, Long> movingAverageMap;

    @Builder
    public MovingAverage(Map<String, Long> movingAverageMap) {
        this.movingAverageMap = movingAverageMap;
    }

    public void addMovingAverage(String windowName, long movingAverage) {
        this.movingAverageMap.put(windowName, movingAverage);
    }

}
