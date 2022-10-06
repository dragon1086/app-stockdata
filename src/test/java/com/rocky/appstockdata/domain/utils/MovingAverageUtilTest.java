package com.rocky.appstockdata.domain.utils;

import com.rocky.appstockdata.domain.DailyDeal;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.rocky.appstockdata.domain.utils.MovingAverageUtil.addMovingAverage;
import static java.util.Arrays.asList;

public class MovingAverageUtilTest {

    @Test
    public void addMovingAverage_FiveDays(){
        List<DailyDeal> result = addMovingAverage(Fixtures.DAILY_DEALS);

        Assertions.assertThat(result.get(0).getMovingAverage().getMovingAverageMap().get("five")).isNull();
        Assertions.assertThat(result.get(3).getMovingAverage().getMovingAverageMap().get("five")).isNull();
        Assertions.assertThat(result.get(4).getMovingAverage().getMovingAverageMap().get("five")).isEqualTo(3L);
        Assertions.assertThat(result.get(9).getMovingAverage().getMovingAverageMap().get("five")).isEqualTo(8L);
        Assertions.assertThat(result.get(24).getMovingAverage().getMovingAverageMap().get("five")).isEqualTo(23L);
        Assertions.assertThat(result.get(25).getMovingAverage().getMovingAverageMap().get("five")).isEqualTo(25L);
    }

    @Test
    public void addMovingAverage_TwentyDays(){
        List<DailyDeal> result = addMovingAverage(Fixtures.DAILY_DEALS);

        Assertions.assertThat(result.get(0).getMovingAverage().getMovingAverageMap().get("twenty")).isNull();
        Assertions.assertThat(result.get(3).getMovingAverage().getMovingAverageMap().get("twenty")).isNull();
        Assertions.assertThat(result.get(4).getMovingAverage().getMovingAverageMap().get("twenty")).isNull();
        Assertions.assertThat(result.get(19).getMovingAverage().getMovingAverageMap().get("twenty")).isEqualTo(11L);
        Assertions.assertThat(result.get(24).getMovingAverage().getMovingAverageMap().get("twenty")).isEqualTo(16L);
        Assertions.assertThat(result.get(25).getMovingAverage().getMovingAverageMap().get("twenty")).isEqualTo(17L);
    }

    private static class Fixtures{
        public static final List<DailyDeal> DAILY_DEALS = asList(
                DailyDeal.builder().closingPrice(1L).build(),
                DailyDeal.builder().closingPrice(2L).build(),
                DailyDeal.builder().closingPrice(3L).build(),
                DailyDeal.builder().closingPrice(4L).build(),
                DailyDeal.builder().closingPrice(5L).build(),
                DailyDeal.builder().closingPrice(6L).build(),
                DailyDeal.builder().closingPrice(7L).build(),
                DailyDeal.builder().closingPrice(8L).build(),
                DailyDeal.builder().closingPrice(9L).build(),
                DailyDeal.builder().closingPrice(10L).build(),
                DailyDeal.builder().closingPrice(11L).build(),
                DailyDeal.builder().closingPrice(12L).build(),
                DailyDeal.builder().closingPrice(13L).build(),
                DailyDeal.builder().closingPrice(14L).build(),
                DailyDeal.builder().closingPrice(15L).build(),
                DailyDeal.builder().closingPrice(16L).build(),
                DailyDeal.builder().closingPrice(17L).build(),
                DailyDeal.builder().closingPrice(18L).build(),
                DailyDeal.builder().closingPrice(19L).build(),
                DailyDeal.builder().closingPrice(20L).build(),
                DailyDeal.builder().closingPrice(21L).build(),
                DailyDeal.builder().closingPrice(22L).build(),
                DailyDeal.builder().closingPrice(23L).build(),
                DailyDeal.builder().closingPrice(24L).build(),
                DailyDeal.builder().closingPrice(26L).build(),
                DailyDeal.builder().closingPrice(28L).build());
    }

}