package com.rocky.appstockdata.domain.utils;

import com.rocky.appstockdata.domain.DailyDeal;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class MovingAverageUtil {
    public static List<DailyDeal> addMovingAverage(List<DailyDeal> dailyDealList) {
        List<DailyDeal> copiedDailyDealList = dailyDealList.stream().map(dailyDeal -> dailyDeal.copy()).collect(Collectors.toList());
        //이동평균(5,20,60,120 기본 적용)
        final int FIVE_DAYS_WINDOW = 5;
        final int TWENTY_DAYS_WINDOW = 20;
        final int SIXTY_DAYS_WINDOW = 60;
        final int ONE_TWENTY_DAYS_WINDOW = 120;

        int dayCount = 0;
        long sumForFiveDays = 0L;
        long sumForTwentyDays = 0L;
        long sumForSixtyDays = 0L;
        long sumForOneTwentyDays = 0L;

        Queue<Long> queueForFiveDays = new LinkedList<>();
        Queue<Long> queueForTwentyDays = new LinkedList<>();
        Queue<Long> queueForSixtyDays = new LinkedList<>();
        Queue<Long> queueForOneTwentyDays = new LinkedList<>();

        for(DailyDeal dailyDeal : copiedDailyDealList){
            dayCount++;
            sumForFiveDays = addMovingAverageWith(FIVE_DAYS_WINDOW, dayCount, sumForFiveDays, queueForFiveDays, dailyDeal);
            sumForTwentyDays = addMovingAverageWith(TWENTY_DAYS_WINDOW, dayCount, sumForTwentyDays, queueForTwentyDays, dailyDeal);
            sumForSixtyDays = addMovingAverageWith(SIXTY_DAYS_WINDOW, dayCount, sumForSixtyDays, queueForSixtyDays, dailyDeal);
            sumForOneTwentyDays = addMovingAverageWith(ONE_TWENTY_DAYS_WINDOW, dayCount, sumForOneTwentyDays, queueForOneTwentyDays, dailyDeal);
        }

        return copiedDailyDealList;
    }

    private static long addMovingAverageWith(int window, int dayCount, long sum, Queue<Long> queue, DailyDeal dailyDeal) {
        sum += dailyDeal.getClosingPrice();
        queue.offer(dailyDeal.getClosingPrice());

        if(dayCount == window){
            dailyDeal.addMovingAverage(String.valueOf(window), Math.round(sum / (double) window));
        }
        if(dayCount > window){
            sum -= queue.poll();
            dailyDeal.addMovingAverage(String.valueOf(window), Math.round(sum / (double) window));
        }
        return sum;
    }
}
