package com.rocky.appstockdata.domain.utils;

import com.rocky.appstockdata.domain.DailyDealHistory;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class DealTrainingUtil {
    public static LocalDate getRandomDate(String earliestDate) {
        LocalDate start = DealTrainingUtil.transformToLocalDate(earliestDate);
        LocalDate end = LocalDate.now();

        return between(start, end);
    }

    public static LocalDate between(LocalDate startInclusive, LocalDate endExclusive) {
        long startEpochDay = startInclusive.toEpochDay();
        long endEpochDay = endExclusive.toEpochDay();
        long randomDay = ThreadLocalRandom
                .current()
                .nextLong(startEpochDay, endEpochDay);

        return LocalDate.ofEpochDay(randomDay);
    }

    public static String transformToDateFormat(String rawData){
        String year = rawData.substring(0, 4);
        String month = rawData.substring(4, 6);
        String day = rawData.substring(6, 8);

        return year + "-" + month + "-" + day;
    }

    public static LocalDate transformToLocalDate(String rawData){
        String year = rawData.substring(0, 4);
        String month = rawData.substring(4, 6);
        String day = rawData.substring(6, 8);

        return LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
    }

    public static LocalDate transformToLocalDateIncludingDash(String rawData){
        String dashRemoved = rawData.replace("-", "");
        return transformToLocalDate(dashRemoved);
    }

    public static List<DailyDealHistory> sortDesc(List<DailyDealHistory> dailyDealHistories) {
        return dailyDealHistories.stream().sorted((dealHistory1, dealHistory2) -> {
                    int result;
                    LocalDate dealHistory1Date = transformToLocalDate(dealHistory1.getDealDate());
                    LocalDate dealHistory2Date = transformToLocalDate(dealHistory2.getDealDate());
                    result = dealHistory2Date.getYear() - dealHistory1Date.getYear();

                    if(result == 0){
                        result = dealHistory2Date.getMonthValue() - dealHistory1Date.getMonthValue();
                    }

                    if(result == 0){
                        result = dealHistory2Date.getDayOfMonth() - dealHistory1Date.getDayOfMonth();
                    }

                    return result;
                })
                .collect(Collectors.toList());
    }

    public static double minMinusPercent(String level) {
        double MIN_MINUS_TWENTY_PERCENT = 0.833333;
        double MIN_MINUS_THIRTY_PERCENT = 0.769231;
        double MIN_MINUS_FIFTY_PERCENT = 0.666667;
        double MIN_MINUS_EIGHTY_PERCENT = 0.555556;

        if("beginner".equals(level)){
            return MIN_MINUS_TWENTY_PERCENT;
        }
        if("intermediate".equals(level)){
            return MIN_MINUS_FIFTY_PERCENT;
        }
        if("master".equals(level)){
            return MIN_MINUS_EIGHTY_PERCENT;
        }

        return MIN_MINUS_THIRTY_PERCENT;
    }

    public static double maxPlusPercent(String level) {
        double MAX_PLUS_TWENTY_PERCENT = 1.25;
        double MAX_PLUS_THIRTY_PERCENT = 1.428571;
        double MAX_PLUS_FIFTY_PERCENT = 2;
        double MAX_PLUS_EIGHTY_PERCENT = 5;

        if("beginner".equals(level)){
            return MAX_PLUS_TWENTY_PERCENT;
        }
        if("intermediate".equals(level)){
            return MAX_PLUS_FIFTY_PERCENT;
        }
        if("master".equals(level)){
            return MAX_PLUS_EIGHTY_PERCENT;
        }

        return MAX_PLUS_THIRTY_PERCENT;
    }
}
