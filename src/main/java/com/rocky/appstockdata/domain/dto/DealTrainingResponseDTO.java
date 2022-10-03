package com.rocky.appstockdata.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class DealTrainingResponseDTO {
    private String companyName;
    private String startDate;
    private String endDate;
    private String itemName;
    private JSONObject lastDailyDealHistory;
    private JSONObject oneDayAgoDailyDealHistory;
    private Double portion;
    private Long slotAmount;
    private double remainingPortion;
    private long remainingSlotAmount;
    private JSONArray dealModifications;
    private Long totalAmount;
    private double valuationPercent;
    private long averageUnitPrice;
    private long currentClosingPrice;
    private String nextTryDate;
    private Long sumOfPurchaseAmount;
    private Long sumOfSellingAmount;
    private Long sumOfCommission;
    private Integer sumOfPurchaseQuantity;
    private Integer sumOfSellingQuantity;
    private Double earningRate;
    private Long earningAmount;
    private boolean isError;
    private String errorMessage;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Builder
    public DealTrainingResponseDTO(String companyName,
                                   LocalDate startDate,
                                   LocalDate endDate,
                                   String itemName,
                                   JSONObject lastDailyDealHistory,
                                   JSONObject oneDayAgoDailyDealHistory,
                                   Double portion,
                                   Long slotAmount,
                                   double remainingPortion,
                                   long remainingSlotAmount,
                                   JSONArray dealModifications,
                                   Long totalAmount,
                                   double valuationPercent,
                                   long averageUnitPrice,
                                   long currentClosingPrice,
                                   String nextTryDate,
                                   Long sumOfPurchaseAmount,
                                   Long sumOfSellingAmount,
                                   Long sumOfCommission,
                                   Integer sumOfPurchaseQuantity,
                                   Integer sumOfSellingQuantity,
                                   Double earningRate,
                                   Long earningAmount,
                                   boolean isError,
                                   String errorMessage) {
        this.companyName = companyName;
        this.startDate = startDate.format(formatter);
        this.endDate = endDate.format(formatter);
        this.itemName = itemName;
        this.lastDailyDealHistory = lastDailyDealHistory;
        this.oneDayAgoDailyDealHistory = oneDayAgoDailyDealHistory;
        this.portion = portion;
        this.slotAmount = slotAmount;
        this.remainingPortion = remainingPortion;
        this.remainingSlotAmount = remainingSlotAmount;
        this.dealModifications = dealModifications;
        this.totalAmount = totalAmount;
        this.valuationPercent = valuationPercent;
        this.averageUnitPrice = averageUnitPrice;
        this.currentClosingPrice = currentClosingPrice;
        this.nextTryDate = nextTryDate;
        this.sumOfPurchaseAmount = sumOfPurchaseAmount;
        this.sumOfSellingAmount = sumOfSellingAmount;
        this.sumOfCommission = sumOfCommission;
        this.sumOfPurchaseQuantity = sumOfPurchaseQuantity;
        this.sumOfSellingQuantity = sumOfSellingQuantity;
        this.earningRate = earningRate;
        this.earningAmount = earningAmount;
        this.isError = isError;
        this.errorMessage = errorMessage;
    }
}
