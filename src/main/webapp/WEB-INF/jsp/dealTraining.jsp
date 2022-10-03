<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <!-- Google Tag Manager -->
    <script>(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
            new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
        j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
        'https://www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
    })(window,document,'script','dataLayer','GTM-T8G7WJD');</script>
    <!-- End Google Tag Manager -->
    <!-- Google AdSense -->
    <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-3126725853648403"
            crossorigin="anonymous"></script>
    <!-- End Google AdSense -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="주식 일봉매매 시뮬레이션">
    <meta name="author" content="펭수르">
    <meta name="generator" content="stock-buildup 0.0.1">
    <title>주식 일봉매매 시뮬레이션</title>
    <!--  부트스트랩 js 사용 -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js@2.9.4/dist/Chart.min.js" integrity="sha384-zNy6FEbO50N+Cg5wap8IKA4M/ZnLJgzc6w2NqACZaK0u0FXfOWRRJOnQtpZun8ha" crossorigin="anonymous"></script>

    <link rel="stylesheet" href="/resources/css/bootstrap.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <style>
        .bd-placeholder-img {
            font-size: 1.125rem;
            text-anchor: middle;
            -webkit-user-select: none;
            -moz-user-select: none;
            user-select: none;
        }

        @media (min-width: 768px) {
            .bd-placeholder-img-lg {
                font-size: 3.5rem;
            }
        }

        input[type='date'] { font-size: 13px; }
    </style>
    <!-- Custom styles for this template -->
    <link href="/resources/css/buildup.css" rel="stylesheet">
    <script>
        //result setting values
        var nextTryDate = "${nextTryDate}";
        var currentClosingPrice = "${currentClosingPrice}";
        var dealModifications = [];
        var dailyDealHistories = [];

        //candleStick setting values
        var itemName = "${itemName}";
        var companyName = "${companyName}";
        var startDate = "${startDate}";
        var endDate = "${endDate}";
        var initialPortion = "${initialPortion}";
        var candleStickDataList = [];
        var volumeList = [];
        var portionList = [];
        var myAverageUnitPriceList = [];
        var additionalBuyingPrice = [];
        var additionalSellingPrice = [];
        var additionalBuyingAmount = [];
        var additionalSellingAmount = [];
        var fiveMovingAverageList = [];
        var twentyMovingAverageList = [];
        var sixtyMovingAverageList = [];
        var oneTwentyMovingAverageList = [];
        var groupingUnits = [['day', [1]], ['week', [1]], ['month', [1, 2, 3, 4, 6]]];
        var earningRate = "${earningRate}";
        var earningAmount = "${earningAmount}";
        var slotAmount = "${slotAmount}";
        var portion = "${portion+((portion%1>0.5)?(1-(portion%1))%1:-(portion%1))}";
        var remainingSlotAmount = "${remainingSlotAmount}";
        var remainingPortion = "${remainingPortion+((remainingPortion%1>0.5)?(1-(remainingPortion%1))%1:-(remainingPortion%1))}";
        var sumOfPurchaseAmount = "${sumOfPurchaseAmount}";
        var sumOfSellingAmount = "${sumOfSellingAmount}";
        var sumOfPurchaseQuantity = "${sumOfPurchaseQuantity}";
        var sumOfSellingQuantity = "${sumOfSellingQuantity}";
        var sumOfCommission = "${sumOfCommission}";
        var totalAmount = "${totalAmount}";
        var valuationPercent = "${valuationPercent}";
        var averageUnitPrice = "${averageUnitPrice}";

        //etc
        var isError = "${isError}"
        var errorMessage = "${errorMessage}";

        $(function(){
            $('input[id=thisModifyDate]').attr('value',nextTryDate);
            $('input[id=sellPercent]').attr('value',"0");
            $('input[id=buyPercent]').attr('value',"0");
            $('input[id=sellPrice]').attr('value',currentClosingPrice);
            $('input[id=buyPrice]').attr('value',currentClosingPrice);
            $(".currentClosingPrice").text(makeComma(currentClosingPrice));
            $(".errorMessage").text(errorMessage);
            $(".earningRate").text(makeComma(earningRate));
            $(".earningAmount").text(makeComma(earningAmount));
            $(".slotAmount").text(makeComma(slotAmount));
            $(".portion").text(makeComma(portion));
            $(".remainingSlotAmount").text(makeComma(remainingSlotAmount));
            $(".remainingPortion").text(makeComma(remainingPortion));
            $(".sumOfPurchaseAmount").text(makeComma(sumOfPurchaseAmount));
            $(".sumOfSellingAmount").text(makeComma(sumOfSellingAmount));
            $(".sumOfPurchaseQuantity").text(makeComma(sumOfPurchaseQuantity));
            $(".sumOfSellingQuantity").text(makeComma(sumOfSellingQuantity));
            $(".sumOfCommission").text(makeComma(sumOfCommission));
            $(".totalAmount").text(makeComma(totalAmount));
            $(".valuationPercent").text(makeComma(valuationPercent));
            $(".averageUnitPrice").text(makeComma(averageUnitPrice));
            $('input[id=inputCompanyName]').attr('value',companyName);
            $('input[id=inputStartDate]').attr('value',startDate);
            $('input[id=inputEndDate]').attr('value',endDate);
            $('input[id=inputSlotAmount]').attr('value',slotAmount);
            $('input[id=inputPortion]').attr('value', makeComma(initialPortion));
            $("#submitButton").on("click",function(event) {
                event.preventDefault(); //submit 메소드 두 번 실행 방지
                var url = "/deal-calculate-modify";
                var zero = "0";
                var modifyDate = $('input[name=modifyDate]').map(function(){return $(this).val();}).get();
                var sellPercent = $('input[name=sellPercent]').map(function(){return $(this).val();}).get();
                var sellPrice = $('input[name=sellPrice]').map(function(){return $(this).val();}).get();
                var buyPercent = $('input[name=buyPercent]').map(function(){return $(this).val();}).get();
                var buyPrice = $('input[name=buyPrice]').map(function(){return $(this).val();}).get();

                var requestData = {
                    modifyDates: modifyDate,
                    sellPercents: sellPercent,
                    sellPrices: sellPrice,
                    buyPercents: buyPercent,
                    buyPrices: buyPrice,
                    companyName: companyName,
                    startDate: startDate,
                    endDate: endDate,
                    slotAmount: slotAmount,
                    portion: portion
                };

                $.ajax({
                    type: "post",
                    url: url,
                    accept: "application/json",
                    dataType: "json",
                    contentType: "application/json",
                    data: JSON.stringify(requestData),
                    success: function(data) {
                        // Ajax call completed successfully
                        nextTryDate = data.nextTryDate;
                        $('input[id=thisModifyDate]').attr('value',nextTryDate);

                        //update result setting values
                        currentClosingPrice = data.currentClosingPrice;
                        $('input[id=sellPrice]').attr('value',currentClosingPrice);
                        $('input[id=buyPrice]').attr('value',currentClosingPrice);
                        $('input[id=sellPercent]').val('0');
                        $('input[id=buyPercent]').val('0');
                        $(".currentClosingPrice").text(makeComma(currentClosingPrice));

                        dealModifications = data.dealModifications;

                        dailyDealHistories.pop();
                        dailyDealHistories.push(data.oneDayAgoDailyDealHistory);
                        dailyDealHistories.push(data.lastDailyDealHistory);

                        //update candleStick setting values
                        itemName = data.itemName;
                        companyName = data.companyName;
                        $('input[id=inputCompanyName]').attr('value',companyName);
                        startDate = data.startDate;
                        $('input[id=inputStartDate]').attr('value',startDate);
                        endDate = data.endDate;
                        $('input[id=inputEndDate]').attr('value',endDate);
                        initialPortion= data.initialPortion;

                        candleStickDataList.pop();
                        candleStickDataList.push([data.oneDayAgoDailyDealHistory.dealDateForTimestamp, data.oneDayAgoDailyDealHistory.startPrice, data.oneDayAgoDailyDealHistory.highPrice, data.oneDayAgoDailyDealHistory.lowPrice, data.oneDayAgoDailyDealHistory.closingPrice]);
                        candleStickDataList.push([data.lastDailyDealHistory.dealDateForTimestamp, data.lastDailyDealHistory.startPrice, data.lastDailyDealHistory.highPrice, data.lastDailyDealHistory.lowPrice, data.lastDailyDealHistory.closingPrice]);

                        volumeList.pop();
                        volumeList.push([data.oneDayAgoDailyDealHistory.dealDateForTimestamp, data.oneDayAgoDailyDealHistory.tradeVolume]);
                        volumeList.push([data.lastDailyDealHistory.dealDateForTimestamp, data.lastDailyDealHistory.tradeVolume]);

                        portionList.pop();
                        portionList.push([data.oneDayAgoDailyDealHistory.dealDateForTimestamp, Number(data.oneDayAgoDailyDealHistory.portion.toFixed(0))]);
                        portionList.push([data.lastDailyDealHistory.dealDateForTimestamp, Number(data.lastDailyDealHistory.portion.toFixed(0))]);
                        if( data.oneDayAgoDailyDealHistory.myAverageUnitPrice !== 0 ) {
                            myAverageUnitPriceList.pop();
                            myAverageUnitPriceList.push([data.oneDayAgoDailyDealHistory.dealDateForTimestamp, data.oneDayAgoDailyDealHistory.myAverageUnitPrice]);
                            myAverageUnitPriceList.push([data.lastDailyDealHistory.dealDateForTimestamp, data.lastDailyDealHistory.myAverageUnitPrice]);
                        }
                        if( data.oneDayAgoDailyDealHistory.additionalBuyingQuantity !== 0 ) {
                            additionalBuyingPrice.pop();
                            additionalBuyingPrice.push([data.oneDayAgoDailyDealHistory.dealDateForTimestamp, data.oneDayAgoDailyDealHistory.additionalBuyingAmount / data.oneDayAgoDailyDealHistory.additionalBuyingQuantity]);
                            additionalBuyingPrice.push([data.lastDailyDealHistory.dealDateForTimestamp, data.lastDailyDealHistory.additionalBuyingAmount / data.lastDailyDealHistory.additionalBuyingQuantity]);

                            additionalBuyingAmount.pop();
                            additionalBuyingAmount.push([data.oneDayAgoDailyDealHistory.dealDateForTimestamp, data.oneDayAgoDailyDealHistory.additionalBuyingAmount]);
                            additionalBuyingAmount.push([data.lastDailyDealHistory.dealDateForTimestamp, data.lastDailyDealHistory.additionalBuyingAmount]);
                        }
                        if( data.oneDayAgoDailyDealHistory.additionalSellingQuantity !== 0 ) {
                            additionalSellingPrice.pop();
                            additionalSellingPrice.push([data.oneDayAgoDailyDealHistory.dealDateForTimestamp, data.oneDayAgoDailyDealHistory.additionalSellingAmount / data.oneDayAgoDailyDealHistory.additionalSellingQuantity]);
                            additionalSellingPrice.push([data.lastDailyDealHistory.dealDateForTimestamp, data.lastDailyDealHistory.additionalSellingAmount / data.lastDailyDealHistory.additionalSellingQuantity]);

                            additionalSellingAmount.pop();
                            additionalSellingAmount.push([data.oneDayAgoDailyDealHistory.dealDateForTimestamp, data.oneDayAgoDailyDealHistory.additionalSellingAmount]);
                            additionalSellingAmount.push([data.lastDailyDealHistory.dealDateForTimestamp, data.lastDailyDealHistory.additionalSellingAmount]);
                        }
                        if( data.oneDayAgoDailyDealHistory.movingAverage.movingAverageMap.five ) {
                            fiveMovingAverageList.pop();
                            fiveMovingAverageList.push([data.oneDayAgoDailyDealHistory.dealDateForTimestamp, data.oneDayAgoDailyDealHistory.movingAverage.movingAverageMap.five]);
                            fiveMovingAverageList.push([data.lastDailyDealHistory.dealDateForTimestamp, data.lastDailyDealHistory.movingAverage.movingAverageMap.five]);
                        }
                        if( data.oneDayAgoDailyDealHistory.movingAverage.movingAverageMap.twenty ) {
                            twentyMovingAverageList.pop();
                            twentyMovingAverageList.push([data.oneDayAgoDailyDealHistory.dealDateForTimestamp, data.oneDayAgoDailyDealHistory.movingAverage.movingAverageMap.twenty]);
                            twentyMovingAverageList.push([data.lastDailyDealHistory.dealDateForTimestamp, data.lastDailyDealHistory.movingAverage.movingAverageMap.twenty]);
                        }
                        if( data.oneDayAgoDailyDealHistory.movingAverage.movingAverageMap.sixty ) {
                            sixtyMovingAverageList.pop();
                            sixtyMovingAverageList.push([data.oneDayAgoDailyDealHistory.dealDateForTimestamp, data.oneDayAgoDailyDealHistory.movingAverage.movingAverageMap.sixty]);
                            sixtyMovingAverageList.push([data.lastDailyDealHistory.dealDateForTimestamp, data.lastDailyDealHistory.movingAverage.movingAverageMap.sixty]);
                        }
                        if( data.oneDayAgoDailyDealHistory.movingAverage.movingAverageMap.oneHundredTwenty ) {
                            oneTwentyMovingAverageList.pop();
                            oneTwentyMovingAverageList.push([data.oneDayAgoDailyDealHistory.dealDateForTimestamp, data.oneDayAgoDailyDealHistory.movingAverage.movingAverageMap.oneHundredTwenty]);
                            oneTwentyMovingAverageList.push([data.lastDailyDealHistory.dealDateForTimestamp, data.lastDailyDealHistory.movingAverage.movingAverageMap.oneHundredTwenty]);
                        }

                        earningRate = data.earningRate;
                        $(".earningRate").text(makeCommaWithDecimal(earningRate));
                        earningAmount = data.earningAmount;
                        $(".earningAmount").text(makeComma(earningAmount));
                        slotAmount = data.slotAmount;
                        $(".slotAmount").text(makeComma(slotAmount));
                        portion = data.portion;
                        $(".portion").text(makeComma(portion));
                        remainingSlotAmount = data.remainingSlotAmount;
                        $(".remainingSlotAmount").text(makeComma(remainingSlotAmount));
                        remainingPortion = data.remainingPortion;
                        $(".remainingPortion").text(makeComma(remainingPortion));
                        sumOfPurchaseAmount = data.sumOfPurchaseAmount;
                        $(".sumOfPurchaseAmount").text(makeComma(sumOfPurchaseAmount));
                        sumOfSellingAmount = data.sumOfSellingAmount;
                        $(".sumOfSellingAmount").text(makeComma(sumOfSellingAmount));
                        sumOfPurchaseQuantity = data.sumOfPurchaseQuantity;
                        $(".sumOfPurchaseQuantity").text(makeComma(sumOfPurchaseQuantity));
                        sumOfSellingQuantity = data.sumOfSellingQuantity;
                        $(".sumOfSellingQuantity").text(makeComma(sumOfSellingQuantity));
                        sumOfCommission = data.sumOfCommission;
                        $(".sumOfCommission").text(makeComma(sumOfCommission));
                        totalAmount = data.totalAmount;
                        $(".totalAmount").text(makeComma(totalAmount));
                        valuationPercent = data.valuationPercent;
                        $(".valuationPercent").text(makeCommaWithDecimal(valuationPercent));
                        averageUnitPrice = data.averageUnitPrice;
                        $(".averageUnitPrice").text(makeComma(averageUnitPrice));

                        //re-draw elements
                        additionalBuyingSellHistory();
                        showDealStatus();
                        drawDealStatus();
                        drawChart();
                        drawAllHistoryTable();
                    },
                    error: function(request, status, error) {
                        // Some error in ajax call
                        alert("code : "+status+"\n message : "+request.responseText+"\n Error : " + error);
                        console.error(error);
                    },
                    complete: function(data){
                        // alert(data.status);
                    }
                });

                return false;
            });
            document.cookie = "SameSite=None; Secure";

            <c:forEach items="${dealModifications}" var="dealModification">
                dealModifications.push(${dealModification});
            </c:forEach>

            additionalBuyingSellHistory();
            showDealStatus();
            drawDealStatus();
            drawCandleStickChart();
        });

        function makeComma(originalNumber){
            var parts = originalNumber.toString().split(".");
            return parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        }

        function makeCommaWithDecimal(originalNumber){
            return originalNumber.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        }

        function additionalBuyingSellHistory(){
            //initilize all childs
            let element = document.getElementById("additionalBuyingSellHistory");
            while (element.firstChild) {
                element.removeChild(element.firstChild);
            }

            for(let idx=0; idx < dealModifications.length; idx++) {
                if( ( dealModifications[idx].buyPercent !== 0 && dealModifications[idx].buyPrice !== 0 ) || ( dealModifications[idx].sellPercent !== 0 && dealModifications[idx].sellPrice !== 0 ) ){
                    var innerDivTagForDate = $('<div/>', {class: 'input-group mb-3'});
                    var innerDivTagForSelling = $('<div/>', {class: 'input-group mb-3'});
                    var innerDivTagForBuying = $('<div/>', {class: 'input-group mb-3'});
                    var spanModifyDate = $('<span/>', {class: 'input-group-text'}).text("수정 날짜");
                    var inputModifyDate = $('<input/>', {readOnly: 'true', type: 'date', class: 'form-control', name: 'modifyDate', value: dealModifications[idx].modifyDate});
                    var spanSellPercentHistory = $('<span/>', {class: 'input-group-text'}).text("매도 비중%");
                    var inputSellPercentHistory = $('<input/>', {readOnly: 'true', type: 'text', class: 'form-control', name: 'sellPercent', value: dealModifications[idx].sellPercent});
                    var spanSellPriceHistory = $('<span/>', {class: 'input-group-text'}).text("매도 가격");
                    var inputSellPriceHistory = $('<input/>', {readOnly: 'true', type: 'text', class: 'form-control', name: 'sellPrice', value: dealModifications[idx].sellPrice});
                    var spanBuyPercentHistory = $('<span/>', {class: 'input-group-text'}).text("매수 비중%");
                    var inputBuyPercentHistory = $('<input/>', {readOnly: 'true', type: 'text', class: 'form-control', name: 'buyPercent', value: dealModifications[idx].buyPercent});
                    var spanBuyPriceHistory = $('<span/>', {class: 'input-group-text'}).text("매수 가격");
                    var inputBuyPriceHistory = $('<input/>', {readOnly: 'true', type: 'text', class: 'form-control', name: 'buyPrice', value: dealModifications[idx].buyPrice});

                    var divTagForDate = document.createElement('div');
                    divTagForDate.className = "input-group mb-3";
                    var divTagForSelling = document.createElement('div');
                    divTagForSelling.className = "input-group mb-3";
                    var divTagForBuying = document.createElement('div');
                    divTagForBuying.className = "input-group mb-3";

                    innerDivTagForDate.append(spanModifyDate, inputModifyDate);
                    innerDivTagForSelling.append(spanSellPercentHistory, inputSellPercentHistory, spanSellPriceHistory, inputSellPriceHistory);
                    innerDivTagForBuying.append(spanBuyPercentHistory, inputBuyPercentHistory, spanBuyPriceHistory, inputBuyPriceHistory);
                    divTagForDate.append(innerDivTagForDate.get(0));
                    divTagForSelling.append(innerDivTagForSelling.get(0));
                    divTagForBuying.append(innerDivTagForBuying.get(0));

                    document.getElementById("additionalBuyingSellHistory").appendChild(divTagForDate).appendChild(divTagForSelling).appendChild(divTagForBuying);
                }
            };
        }

        function showDealStatus() {
            var dealError = document.getElementById("dealError");
            var dealStatus = document.getElementById("dealStatus");
            if (isError === 'true') {
                dealError.style.display = "block";
                dealStatus.style.display = "none";
            } else {
                dealError.style.display = "none";
                dealStatus.style.display = "block";
            }
        }

        function drawDealStatus() {
            var dealStatusWithEarningRate = document.getElementById("dealStatusWithEarningRate");
            var dealStatusWithEmptyEarningRate = document.getElementById("dealStatusWithEmptyEarningRate");
            if (earningRate) {
                dealStatusWithEarningRate.style.display = "block";
                dealStatusWithEmptyEarningRate.style.display = "none";
            } else {
                dealStatusWithEarningRate.style.display = "none";
                dealStatusWithEmptyEarningRate.style.display = "block";
            }
        }

        function drawAllHistoryTable(){
            //initialize
            document.getElementById("dailyHistoryTableBody").innerHTML = "";

            var rows = "";
            for(let idx=0; idx < dailyDealHistories.length; idx++) {
                if( dailyDealHistories[idx].myAverageUnitPrice !== 0 ){
                    rows += '<tr>' +
                        '<td>' + dailyDealHistories[idx].dealDate + '</td>' +
                        '<td>' + makeComma(dailyDealHistories[idx].buyPrice) + '원</td>' +
                        '<td>' + makeComma(dailyDealHistories[idx].buyPercent) + '%</td>' +
                        '<td>' + makeComma(dailyDealHistories[idx].sellPrice) + '원</td>' +
                        '<td>' + makeComma(dailyDealHistories[idx].sellPercent) + '%</td>' +
                        '<td>' + makeComma(dailyDealHistories[idx].myAverageUnitPrice) + '원</td>' +
                        '<td>' + makeComma(dailyDealHistories[idx].additionalBuyingQuantity) + '주</td>' +
                        '<td>' + makeComma(dailyDealHistories[idx].additionalBuyingAmount) + '원</td>' +
                        '<td>' + makeComma(dailyDealHistories[idx].additionalSellingQuantity) + '주</td>' +
                        '<td>' + makeComma(dailyDealHistories[idx].additionalSellingAmount) + '원</td>' +
                        '<td>' + makeComma(dailyDealHistories[idx].commission) + '원</td>' +
                        '<td>' + makeComma(dailyDealHistories[idx].realizedEarningAmount) + '원</td>' +
                        '<td>' + makeComma(dailyDealHistories[idx].remainingAmount) + '원</td>' +
                        '</tr>';
                }
            };

            // get the current table body html as a string, and append the new row
            var html = document.getElementById("dailyHistoryTableBody").innerHTML + rows;

            // set the table body to the new html code
            document.getElementById("dailyHistoryTableBody").innerHTML = html;
        }

        function drawCandleStickChart(){
            <c:forEach items="${dailyDealHistories}" var="dailyDealHistory">
            dailyDealHistories.push(${dailyDealHistory});
            </c:forEach>

            if (isError === 'false'){
                for(let idx=0; idx < dailyDealHistories.length; idx++) {
                    candleStickDataList.push([dailyDealHistories[idx].dealDateForTimestamp, dailyDealHistories[idx].startPrice, dailyDealHistories[idx].highPrice, dailyDealHistories[idx].lowPrice, dailyDealHistories[idx].closingPrice]);
                    volumeList.push([dailyDealHistories[idx].dealDateForTimestamp, dailyDealHistories[idx].tradeVolume]);
                    portionList.push([dailyDealHistories[idx].dealDateForTimestamp, Number(dailyDealHistories[idx].portion.toFixed(0))]);
                    if( dailyDealHistories[idx].myAverageUnitPrice !== 0 ) {
                        myAverageUnitPriceList.push([dailyDealHistories[idx].dealDateForTimestamp,dailyDealHistories[idx].myAverageUnitPrice]);
                    }
                    if( dailyDealHistories[idx].additionalBuyingQuantity !== 0 ) {
                        additionalBuyingPrice.push([dailyDealHistories[idx].dealDateForTimestamp, dailyDealHistories[idx].additionalBuyingAmount / dailyDealHistories[idx].additionalBuyingQuantity]);
                        additionalBuyingAmount.push([dailyDealHistories[idx].dealDateForTimestamp, dailyDealHistories[idx].additionalBuyingAmount]);
                    }
                    if( dailyDealHistories[idx].additionalSellingQuantity !== 0 ) {
                        additionalSellingPrice.push([dailyDealHistories[idx].dealDateForTimestamp, dailyDealHistories[idx].additionalSellingAmount / dailyDealHistories[idx].additionalSellingQuantity]);
                        additionalSellingAmount.push([dailyDealHistories[idx].dealDateForTimestamp, dailyDealHistories[idx].additionalSellingAmount]);
                    }
                    if( dailyDealHistories[idx].movingAverage.movingAverageMap.five ) {
                        fiveMovingAverageList.push([dailyDealHistories[idx].dealDateForTimestamp, dailyDealHistories[idx].movingAverage.movingAverageMap.five]);
                    }
                    if( dailyDealHistories[idx].movingAverage.movingAverageMap.twenty ) {
                        twentyMovingAverageList.push([dailyDealHistories[idx].dealDateForTimestamp, dailyDealHistories[idx].movingAverage.movingAverageMap.twenty]);
                    }
                    if( dailyDealHistories[idx].movingAverage.movingAverageMap.sixty ) {
                        sixtyMovingAverageList.push([dailyDealHistories[idx].dealDateForTimestamp, dailyDealHistories[idx].movingAverage.movingAverageMap.sixty]);
                    }
                    if( dailyDealHistories[idx].movingAverage.movingAverageMap.oneHundredTwenty ) {
                        oneTwentyMovingAverageList.push([dailyDealHistories[idx].dealDateForTimestamp, dailyDealHistories[idx].movingAverage.movingAverageMap.oneHundredTwenty]);
                    }
                };
            }

            document.addEventListener('DOMContentLoaded', drawChart());
        }
        function drawChart() {
            const highchartsOptions = Highcharts.setOptions({
                    lang: {
                        loading: '로딩중입니다...',
                        months: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
                        weekdays: ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'],
                        shortMonths: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
                        exportButtonTitle: "Export",
                        printButtonTitle: "프린트",
                        rangeSelectorFrom: "From",
                        rangeSelectorTo: "To",
                        rangeSelectorZoom: "확대범위",
                        downloadPNG: 'PNG로 다운로드',
                        downloadJPEG: 'JPEG로 다운로드',
                        downloadPDF: 'PDF로 다운로드',
                        downloadSVG: 'SVG로 다운로드',
                        resetZoom: "Reset",
                        resetZoomTitle: "Reset",
                        thousandsSep: ",",
                        decimalPoint: '.'
                    }
                }
            );
            const chart = Highcharts.stockChart('container', {
                title: {
                    text: '일봉차트와 평균단가 그래프(' + itemName + ')',
                    style:{
                        color: '#00443a',
                        fontSize: '24px',
                        fontWeight: 'bold'
                    }
                },
                subtitle: {
                    text: '<b>캔들이 좁게 보일 때 범례의 평균/매수/매도 단가를 클릭해서 없애면, 캔들차트가 더 잘 보입니다</b>',
                    align: 'left'
                },
                chart: {
                    zoomType: 'x'
                },
                time:{
                    useUTC: false,
                    timezone: 'Asia/Seoul',
                },
                legend: {
                    enabled: true,
                    align: 'center',
                    backgroundColor: '#FCFFC5',
                    borderColor: 'black',
                    borderWidth: 2,
                    shadow: true
                },
                rangeSelector: {
                    selected: 1,
                },
                yAxis: [{
                    labels: {
                        align: 'right',
                        x: -3
                    },
                    title: {
                        align: 'high',
                        offset: 0,
                        rotation: 0,
                        y: -10,
                        x: -20,
                        reserveSpace: false,
                        text: '가격'
                    },
                    height: '55%',
                    lineWidth: 2,
                    resize: {
                        enabled: true
                    }
                }, {
                    labels: {
                        align: 'right',
                        x: -3
                    },
                    title: {
                        align: 'high',
                        offset: 0,
                        rotation: 0,
                        y: -10,
                        x: -30,
                        reserveSpace: false,
                        text: '거래량'
                    },
                    top: '60%',
                    height: '10%',
                    offset: 0,
                    lineWidth: 2
                }, {
                    labels: {
                        align: 'right',
                        x: -3
                    },
                    title: {
                        align: 'high',
                        offset: 0,
                        rotation: 0,
                        y: -10,
                        x: -20,
                        reserveSpace: false,
                        text: '비중'
                    },
                    top: '75%',
                    height: '10%',
                    offset: 0,
                    lineWidth: 2
                }, {
                    labels: {
                        align: 'right',
                        x: -3
                    },
                    title: {
                        align: 'high',
                        textAlign: 'left',
                        offset: 0,
                        rotation: 0,
                        y: -10,
                        x: -60,
                        reserveSpace: false,
                        text: '매수/매도 금액'
                    },
                    top: '90%',
                    height: '10%',
                    offset: 0,
                    lineWidth: 2
                }],
                plotOptions: {
                    candlestick: {
                        color: 'blue',
                        upColor: 'red'
                    }
                },
                tooltip: {
                    split: true
                },
                series: [{
                    id: 'candle',
                    name: itemName,
                    type: 'candlestick',
                    data: candleStickDataList,
                    tooltip: {
                        valueDecimals: 0
                    },
                    dataGrouping: {
                        units: groupingUnits
                    }
                }, {
                    type: 'column',
                    name: '거래량',
                    data: volumeList,
                    yAxis: 1,
                    dataGrouping: {
                        units: groupingUnits
                    }
                }, {
                    type: 'spline',
                    name: '평균단가',
                    data: myAverageUnitPriceList,
                    dataGrouping: {
                        units: groupingUnits
                    },
                    color: '#b4aa36',
                    lineWidth: 4,
                    onSeries: 'candle'
                }, {
                    type: 'scatter',
                    name: '추가 매수단가',
                    data: additionalBuyingPrice,
                    dataGrouping: {
                        units: groupingUnits
                    },
                    color: '#afa400',
                    onSeries: 'candle',
                    dataLabels: {
                        enabled: true,
                        borderRadius: 20,
                        borderColor: 'red',
                        y: -5,
                        shape: 'callout',
                        rotation: 20
                    },
                    marker: {
                        symbol: 'url(resources/images/redArrow.jpg)'
                    }
                }, {
                    type: 'scatter',
                    name: '추가 매도단가',
                    data: additionalSellingPrice,
                    dataGrouping: {
                        units: groupingUnits
                    },
                    color: '#007eff',
                    onSeries: 'candle',
                    dataLabels: {
                        enabled: true,
                        borderRadius: 20,
                        borderColor: 'blue',
                        y: 5,
                        shape: null,
                        rotation: 20
                    },
                    marker: {
                        symbol: 'url(resources/images/blueArrow.jpg)'
                    }
                }, {
                    type: 'spline',
                    name: '5일 이동평균',
                    data: fiveMovingAverageList,
                    dataGrouping: {
                        units: groupingUnits
                    },
                    color: '#383832',
                    lineWidth: 1,
                    onSeries: 'candle'
                }, {
                    type: 'spline',
                    name: '20일 이동평균',
                    data: twentyMovingAverageList,
                    dataGrouping: {
                        units: groupingUnits
                    },
                    color: '#ff0000',
                    lineWidth: 1,
                    onSeries: 'candle'
                }, {
                    type: 'spline',
                    name: '60일 이동평균',
                    data: sixtyMovingAverageList,
                    dataGrouping: {
                        units: groupingUnits
                    },
                    color: '#514fff',
                    lineWidth: 1,
                    onSeries: 'candle'
                }, {
                    type: 'spline',
                    name: '120일 이동평균',
                    data: oneTwentyMovingAverageList,
                    dataGrouping: {
                        units: groupingUnits
                    },
                    color: '#ffae00',
                    lineWidth: 1,
                    onSeries: 'candle'
                }, {
                    type: 'line',
                    id: 'portion',
                    name: '비중',
                    data: portionList,
                    dataGrouping: {
                        units: groupingUnits
                    },
                    dataLabels: {
                        enabled: true
                    },
                    yAxis: 2,
                    color: '#000000',
                    onSeries: 'candle'
                }, {
                    type: 'column',
                    id: 'buyingAmount',
                    name: '매수 금액',
                    data: additionalBuyingAmount,
                    dataGrouping: {
                        units: groupingUnits
                    },
                    yAxis: 3,
                    color: '#FF0000'
                }, {
                    type: 'column',
                    name: '매도 금액',
                    data: additionalSellingAmount,
                    dataGrouping: {
                        units: groupingUnits
                    },
                    yAxis: 3,
                    color: '#0022ff',
                    onSeries: 'buyingAmount'
                }],
                responsive: {
                    rules: [{
                        condition: {
                            maxWidth: 800
                        },
                        chartOptions: {
                            rangeSelector: {
                                inputEnabled: false
                            }
                        }
                    }]
                }
            });
        }

    </script>
</head>
<body>
<div class="container">
    <header class="d-flex flex-wrap justify-content-center py-3 mb-4 border-bottom">
        <ul class="nav nav-pills">
            <li class="nav-item"><a href="/" class="nav-link active" aria-current="page">초기화면</a></li>
        </ul>
    </header>
</div>

<div class="container">
    <div class="container-fluid">
        <div class="row">
            <main class="col-md-12 ms-sm-auto col-lg-12 px-md-4">
                <form id="modifyCalculation" name="calculateRequestFrom">
                    <div class="px-4 py-5 my-5 text-left" id="parentDivForModifiyCalculation">
                        <h2><strong>매매 시뮬레이션</strong></h2>
                        <p></p>
                        <h4>아래 그래프를 보시고, 매수/매도/PASS를 선택하세요. </h4>
                        <h4>매수/매도 하고 싶으시면 단가와 비중 입력 후 "다음", 그냥 넘기고 싶으시면 "다음"버튼 눌러주세요. </h4>
                        <h4>1개 행에 매수/매도 다 입력해도 되고, 한쪽만 입력해도 됩니다.</h4>
                        <h4>단, 매수/매도 비중을 입력했으면 매수/매도 단가도 필수입니다. 비중과 단가 중 하나라도 미입력 시 계산 무시합니다.</h4>
                        <h4>매도 시 매도금액의 0.3%를 수수료차원에서 실현손익에서 뺍니다.</h4>
                        <div></div>
                        <div class="input-group mb-3" id= "modifyInputGroup">
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="basic-addon1">현재 날짜</span>
                                <input readonly="true" id="thisModifyDate" type="date" class="form-control" name="modifyDate" aria-label="modifyDate" aria-describedby="basic-addon1">
                            </div>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="basic-addon2">매도 비중%</span>
                                <input id="sellPercent" type="text" class="form-control" name="sellPercent" placeholder="% 제외하고 입력하세요(소수점 제외)" aria-label="sellPercent" aria-describedby="basic-addon2">
                                <span class="input-group-text" id="basic-addon3">매도 가격</span>
                                <input id="sellPrice" type="text" class="form-control" name="sellPrice" placeholder="매도하실 금액을 입력하세요(저가와 고가 사이)" aria-label="sellPrice" aria-describedby="basic-addon3">
                            </div>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="basic-addon4">매수 비중%</span>
                                <input id="buyPercent" type="text" class="form-control" name="buyPercent" placeholder="% 제외하고 입력하세요(소수점 제외)" aria-label="buyPercent" aria-describedby="basic-addon4">
                                <span class="input-group-text" id="basic-addon5">매수 가격</span>
                                <input id="buyPrice" type="text" class="form-control" name="buyPrice" placeholder="매수하실 금액을 입력하세요(저가와 고가 사이)" aria-label="buyPrice" aria-describedby="basic-addon5">
                            </div>
                        </div>
                    </div>

                    <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
                        <button type="button" id="submitButton" class="btn btn-primary btn-lg px-4 gap-3">다음</button>
                    </div>

                    <hr style="height:3px;color:#dc874f">

                    <div id="container" style="height: 1000px; min-width: 310px"></div>

                    <hr style="height:3px;color:#dc874f">

                    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3">
                        <div>
                                <div id="dealError" disabled="false">
                                    <h1><span class="errorMessage"></span></h1>
                                </div>
                                <div id="dealStatus" disabled="true">
                                    <h1><strong>매매 현황</strong></h1>
                                    <p></p>
                                    <div id="dealStatusWithEmptyEarningRate" disabled="false">
                                        <h2>실현수익률 : 0%</h2>
                                        <h2>실현손익 : 0원</h2>
                                        <h2>슬랏 할당금액 : <span class="slotAmount"></span>원</h2>
                                        <h2>현재 비중 : <span class="portion"></span>%</h2>
                                        <h2>슬랏 예수금 : <span class="remainingSlotAmount"></span>원</h2>
                                        <h2>슬랏 예수금 비중 : <span class="remainingPortion"></span>%</h2>
                                        <h2>총 매입금액 : <span class="sumOfPurchaseAmount"></span>원</h2>
                                        <h2>총 매도금액 : 0원</h2>
                                        <h2>총 매입수량 : <span class="sumOfPurchaseQuantity"></span>주</h2>
                                        <h2>총 매도수량 : 0주</h2>
                                        <h2>총 매도수수료(0.3%) : 0원</h2>
                                        <h2>현재 평가금액 : <span class="totalAmount"></span>원</h2>
                                        <h2>현재 평가손익 : <span class="valuationPercent"></span>%</h2>
                                        <h2>현재 평균단가 : <span class="averageUnitPrice"></span>원</h2>
                                        <h2>현재 종가 : <span class="currentClosingPrice"></span>원</h2>
                                    </div>
                                    <div id="dealStatusWithEarningRate" disabled="true">
                                            <h2>실현수익률 : <span class="earningRate"></span>%</h2>
                                            <h2>실현손익 : <span class="earningAmount"></span>원</h2>
                                            <h2>슬랏 할당금액 : <span class="slotAmount"></span>원</h2>
                                            <h2>현재 비중 : <span class="portion"></span>%</h2>
                                            <h2>슬랏 예수금 : <span class="remainingSlotAmount"></span>원</h2>
                                            <h2>슬랏 예수금 비중 : <span class="remainingPortion"></span>%</h2>
                                            <h2>총 매입금액 : <span class="sumOfPurchaseAmount"></span>원</h2>
                                            <h2>총 매도금액 : <span class="sumOfSellingAmount"></span>원</h2>
                                            <h2>총 매입수량 : <span class="sumOfPurchaseQuantity"></span>주</h2>
                                            <h2>총 매도수량 : <span class="sumOfSellingQuantity"></span>주</h2>
                                            <h2>총 매도수수료(0.3%) : <span class="sumOfCommission"></span>원</h2>
                                            <h2>현재 평가금액 : <span class="totalAmount"></span>원</h2>
                                            <h2>현재 평가손익 : <span class="valuationPercent"></span>%</h2>
                                            <h2>현재 평균단가 : <span class="averageUnitPrice"></span>원</h2>
                                            <h2>현재 종가 : <span class="currentClosingPrice" ></span>원</h2>
                                    </div>
                                </div>
                        </div>
                    </div>

                    <hr style="height:3px;color:#dc874f">
                    <div class="px-4 py-5 my-5 text-left">
                        <h2><strong>최초 입력 요청값</strong></h2>
                        <div class="input-group mb-3">
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="input-addon1">기업 이름</span>
                                <input readonly="true" type="text" id="inputCompanyName" class="form-control" name="companyName" aria-label="companyName" aria-describedby="input-addon1" value="">
                            </div>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="input-addon2">시작 날짜</span>
                                <input readonly="true" type="date" id="inputStartDate" class="form-control" name="startDate" aria-label="startDate" aria-describedby="input-addon2" value="">
                            </div>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="input-addon3">최근 날짜</span>
                                <input readonly="true" type="date" id="inputEndDate" class="form-control" name="endDate" aria-label="endDate" aria-describedby="input-addon3" value="">
                            </div>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="input-addon4">설정 금액</span>
                                <input readonly="true" type="text" id="inputSlotAmount" class="form-control" name="slotAmount" aria-label="slotAmount" aria-describedby="input-addon4" value="">
                                <span class="input-group-text" id="input-addon5">시작 비중%</span>
                                <input readonly="true" type="text" id="inputPortion" class="form-control" name="portion" aria-label="portion" aria-describedby="input-addon5" value="">
                            </div>
                        </div>
                    </div>

                    <hr style="height:3px;color:#dc874f">
                    <div class="px-4 py-5 my-5 text-left" id="additionalBuyingSellHistory">
                        <h2><strong>추가 매수/매도 이력</strong></h2>
                    </div>
                </form>

                <hr style="height:3px;color:#dc874f">
                <div class="px-4 my-5 text-left">
                    <h2><strong>날짜별 전체 이력</strong></h2>
                    <div class="table-responsive">
                        <table class="table table-striped table-sm">
                            <thead>
                            <tr>
                                <th scope="col">거래일</th>
                                <th scope="col">매수단가</th>
                                <th scope="col">매수비중</th>
                                <th scope="col">매도단가</th>
                                <th scope="col">매도비중</th>
                                <th scope="col">내 평단</th>
                                <th scope="col">추가 구매수량</th>
                                <th scope="col">추가 구매금액</th>
                                <th scope="col">추가 매도수량</th>
                                <th scope="col">추가 매도금액</th>
                                <th scope="col">매도 시 수수료</th>
                                <th scope="col">매도 시 실현손익</th>
                                <th scope="col">예수금</th>
                            </tr>
                            </thead>
                            <tbody id="dailyHistoryTableBody">
                            </tbody>
                        </table>
                    </div>
                </div>
            </main>
        </div>
    </div>
</div>

<!-- Footer -->
<footer class="text-center text-lg-start bg-light text-muted">
    <!-- Copyright -->
    <div class="text-center p-4" style="background-color: rgba(0, 0, 0, 0.05);">
        개발자(펭수르) 개별 문의처 :
        <a class="text-reset fw-bold">dragon1086@naver.com</a>
        <p></p>
        주식 데이터 구매 문의처 :
        <a class="text-reset fw-bold" href="https://kmong.com/gig/245871">https://kmong.com/gig/245871</a>
    </div>
    <!-- Copyright -->
</footer>
<!-- Footer -->

<script type="text/javascript" src="/resources/js/bootstrap.js?ver=123"></script>
<script src="https://cdn.jsdelivr.net/npm/feather-icons@4.28.0/dist/feather.min.js" integrity="sha384-uO3SXW5IuS1ZpFPKugNNWqTZRRglnUJK6UAZ/gxOX80nxEkN9NcGZTftn6RzhGWE" crossorigin="anonymous"></script>
<script src="https://code.highcharts.com/stock/highstock.js"></script>
<script src="https://code.highcharts.com/stock/modules/exporting.js"></script>

</body>
</html>