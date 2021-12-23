package com.rocky.appstockdata.domain.utils;

import com.rocky.appstockdata.domain.DailyDealHistory;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class DealTrainingUtil {
    public static LocalDate getRandomDate() {
        LocalDate start = LocalDate.of(2002, Month.JANUARY, 1);
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
}
