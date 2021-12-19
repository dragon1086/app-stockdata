package com.rocky.appstockdata.domain.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class BuildUpUtil {
    public static long transformDate(String dealDate) {
        int year = Integer.parseInt(dealDate.substring(0,4));
        int month = Integer.parseInt(dealDate.substring(4,6));
        int day = Integer.parseInt(dealDate.substring(6,8));

        return ZonedDateTime.of(year, month, day, 0, 0, 0, 0, ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli();
    }
}
