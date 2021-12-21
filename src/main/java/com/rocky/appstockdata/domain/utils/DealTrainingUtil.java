package com.rocky.appstockdata.domain.utils;

import java.time.LocalDate;
import java.time.Month;
import java.util.concurrent.ThreadLocalRandom;

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
}
