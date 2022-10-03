package com.rocky.appstockdata.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class MovingAverage {
    private Map<String, Long> movingAverageMap;
    private final String FIVE_DAY = "five";
    private final String TWENTY_DAY = "twenty";
    private final String SIXTY_DAY = "sixty";
    private final String ONE_HUNDRED_TWENTY_DAY = "oneHundredTwenty";

    @Builder
    public MovingAverage(Map<String, Long> movingAverageMap) {
        this.movingAverageMap = movingAverageMap;
    }

    public void addMovingAverage(String windowName, long movingAverage) {
        this.movingAverageMap.put(changeWindowName(windowName), movingAverage);
    }

    private String changeWindowName(String windowName) {
        switch(windowName){
            case "5":
                return FIVE_DAY;
            case "20":
                return TWENTY_DAY;
            case "60":
                return SIXTY_DAY;
            case "120":
                return ONE_HUNDRED_TWENTY_DAY;
            default:
                return windowName;
        }
    }

}
