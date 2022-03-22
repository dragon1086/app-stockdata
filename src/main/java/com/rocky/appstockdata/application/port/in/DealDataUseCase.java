package com.rocky.appstockdata.application.port.in;

public interface DealDataUseCase {
    String from3MonthsAgoToTodayDeals(String id);

    String from6MonthsAgoTo3MonthsAgoDeals(String id);

    String from9MonthsAgoTo6MonthsAgoDeals(String id);

    String from12MonthsAgoTo9MonthsAgoDeals(String id);

    String from1MonthsAgoToTodayDeals(String id);

    String from2MonthsAgoTo1MonthsAgoDeals(String id);
}
