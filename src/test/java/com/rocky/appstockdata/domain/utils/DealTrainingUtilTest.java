package com.rocky.appstockdata.domain.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

public class DealTrainingUtilTest {
    @Test
    public void randomDateTest(){
        LocalDate start = LocalDate.of(1989, Month.OCTOBER, 14);
        LocalDate end = LocalDate.now();
        LocalDate random = DealTrainingUtil.between(start, end);
        assertThat(random).isBetween(start, end);
    }

}