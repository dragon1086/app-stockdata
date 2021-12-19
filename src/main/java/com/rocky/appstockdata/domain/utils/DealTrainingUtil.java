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
}
