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
<%--    <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-3126725853648403"--%>
<%--            crossorigin="anonymous"></script>--%>
    <!-- End Google AdSense -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="주식 일봉매매 시뮬레이션">
    <meta name="author" content="펭수르">
    <meta name="generator" content="stock-buildup 0.0.1">
    <title>주식 일봉매매 시뮬레이션</title>
    <!--  부트스트랩 js 사용 -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js@2.9.4/dist/Chart.min.js" integrity="sha384-zNy6FEbO50N+Cg5wap8IKA4M/ZnLJgzc6w2NqACZaK0u0FXfOWRRJOnQtpZun8ha" crossorigin="anonymous"></script>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

    <style>
        body {
            font-family: 'Noto Sans KR', sans-serif;
            background-color: #f8f9fa;
            padding-top: 56px;
        }

        .navbar {
            background-color: #3498db;
            padding: 0.5rem 1rem;
        }

        .navbar-brand {
            color: #ffffff !important;
            font-size: 1.75rem;
            font-weight: bold;
            display: flex;
            align-items: center;
        }

        .navbar-nav .nav-link {
            color: inherit;
            padding: 0;
            margin: 0;
        }

        .navbar-toggler {
            border-color: rgba(255, 255, 255, 0.5);
        }

        .navbar-collapse {
            justify-content: flex-end;
        }

        .btn-auto-sim,
        .btn-outline-light {
            background-color: transparent !important;
            color: #ffffff !important;
            border: 1px solid #ffffff !important;
            padding: 0.375rem 0.75rem !important;
            transition: background-color 0.3s ease, color 0.3s ease !important;
            font-size: 1rem !important;
            margin-right: 1rem !important;
            text-decoration: none !important;
            display: inline-flex !important;
            align-items: center !important;
            justify-content: center !important;
            height: 38px !important;
            line-height: 1.5 !important;
            text-align: center !important;
        }

        .btn-home:hover,
        .btn-auto-sim:hover,
        .btn-outline-light:hover {
            background-color: #ffffff !important;
            color: #3498db !important;
        }

        .btn-home {
            background-color: transparent !important;
            color: #ffffff !important;
            border: 1px solid #ffffff !important;
            padding: 0.375rem 0.75rem !important;
            transition: background-color 0.3s ease, color 0.3s ease !important;
            font-size: 1rem !important;
            margin-right: 1rem !important;
            text-decoration: none !important;
            display: inline-flex !important;
            align-items: center !important;
            justify-content: center !important;
            height: 38px !important;
            line-height: 1.5 !important;
            text-align: center !important;
            width: 120px; /* 버튼의 폭을 고정 */
        }

        .btn-home i {
            margin-right: 0.5rem;
        }

        .btn-home span {
            flex-grow: 1;
            text-align: center;
        }

        .btn-info {
            background-color: #2ecc71;
            border-color: #2ecc71;
            color: #ffffff;
        }

        .btn-info:hover {
            background-color: #27ae60;
        }

        .main-container {
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 0 30px rgba(0,0,0,0.1);
            padding: 3rem;
            margin-top: 3rem;
        }

        .form-label {
            font-weight: bold;
            color: #2c3e50;
        }

        .btn-primary {
            background-color: #3498db;
            border-color: #3498db;
        }

        .btn-primary:hover {
            background-color: #2980b9;
        }

        footer {
            background-color: #000000;
            color: #ffffff;
        }
        footer a {
            color: #3498db;
            text-decoration: none;
        }
        footer a:hover {
            color: #5dade2;
            text-decoration: underline;
        }
        .aqr-qr-container {
            display: flex;
            flex-direction: column;
            align-items: center;
            background-color: rgba(255, 255, 255, 0.05);
            border-radius: 10px;
            padding: 20px;
            border: 1px solid rgba(255, 255, 255, 0.1);
        }
        .qr-code-wrapper {
            text-align: center;
            background-color: #ffffff;
            padding: 10px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(255, 255, 255, 0.1);
        }
        .qr-code-wrapper img {
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        .qr-code-wrapper p {
            color: #000000;
            font-size: 0.9rem;
            font-weight: bold;
        }
        .contact-info {
            margin-top: 20px;
        }
        .contact-info a {
            color: #5dade2;
        }
        .contact-info a:hover {
            color: #3498db;
        }

        .tooltip-icon {
            cursor: pointer;
            color: #3498db;
            margin-left: 0.5rem;
        }

        .navbar-nav,
        .navbar-nav .nav-item,
        .d-flex {
            display: flex !important;
            align-items: center !important;
        }

        .btn-download {
            background-color: #3498db;
            color: #ffffff;
            border: none;
            padding: 0.5rem 1rem;
            font-size: 1rem;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
            display: inline-block;
            margin-top: 1rem;
        }

        .btn-download:hover {
            background-color: #2980b9;
        }

        .download-section {
            text-align: center;
            margin-top: 2rem;
            padding: 1rem;
            background-color: #f8f9fa;
            border-radius: 5px;
        }
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
        var historyId = "${historyId}";

        //etc
        var isError = "${isError}"
        var errorMessage = "${errorMessage}";

        //chart
        let chartInstance;
        let isHeikinAshi = false;

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
            $("#copyButton").on("click",function(event) {
                event.preventDefault(); //submit 메소드 두 번 실행 방지
                copyChart();
                return false;
            });
            $("#submitButton").on("click",function(event) {
                event.preventDefault(); //submit 메소드 두 번 실행 방지
                submitChart();
                return false;
            });
            $("#downloadHistoriesBtn").on("click",function(event) {
                event.preventDefault(); //submit 메소드 두 번 실행 방지
                downloadDealHistories();
                return false;
            });
            // 토글 버튼 클릭 이벤트 처리
            $(document).on("click", "#candlestick-heikinashi-toggle", function(event) {
                event.preventDefault();

                if (!chartInstance) {
                    console.error('Chart instance not found');
                    return false;
                }

                const candleSeries = chartInstance.get('candle');
                if (!candleSeries) {
                    console.error('Candle series not found');
                    return false;
                }

                isHeikinAshi = !isHeikinAshi; //상태 토글

                candleSeries.update({
                    type: 'candlestick',
                    data: isHeikinAshi ? calculateHeikinAshi(candleStickDataList) : candleStickDataList,
                    name: itemName + (isHeikinAshi ? ' (하이킨아시)' : ''),
                    isHeikinAshi: isHeikinAshi
                }, false);

                chartInstance.redraw();

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

        // 다운로드 버튼 클릭 이벤트
        function downloadDealHistories() {
            console.log('Download dailyDealHistories');
            try {
                const csvContent = convertToCSV(dailyDealHistories);
                const fileName = 'daily_deal_histories.csv';
                downloadCSV(csvContent, fileName);
            } catch (error) {
                console.error('CSV 다운로드 중 오류 발생:', error);
                alert('CSV 파일 생성 중 오류가 발생했습니다. 콘솔을 확인해주세요.');
            }
        };


        function submitChart(){
            var url = "/deal-calculate-modify";
            var modifyDate = $('input[name=modifyDate]').map(function(){return $(this).val();}).get();
            var sellPercent = $('input[name=sellPercent]').map(function(){return $(this).val();}).get();
            var sellPrice = $('input[name=sellPrice]').map(function(){return $(this).val();}).get();
            var buyPercent = $('input[name=buyPercent]').map(function(){return $(this).val();}).get();
            var buyPrice = $('input[name=buyPrice]').map(function(){return $(this).val();}).get();
            var jumpDate = $('input[name=jumpDate]').map(function(){return $(this).val();}).get()[0];

            if(!validatePrices(sellPrice, buyPrice)){
                return false;
            }

            if(!confirmPercents(sellPercent, buyPercent)){
                return false;
            }

            if(!validateJumpDate(sellPercent, buyPercent, jumpDate)){
                return false;
            }

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
                portion: portion,
                initialPortion: initialPortion,
                jumpDate: jumpDate,
                historyId: historyId
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
                    historyId = data.historyId;
                    $('input[id=sellPrice]').prop('value',currentClosingPrice);
                    $('input[id=buyPrice]').prop('value',currentClosingPrice);
                    $('input[id=sellPercent]').val('0');
                    $('input[id=buyPercent]').val('0');
                    $(".currentClosingPrice").text(makeComma(currentClosingPrice));
                    $('input[id=jumpDate]').val('');

                    dealModifications = data.dealModifications;

                    if(isEmptyJsonArray(data.deltaDailyDealHistories)) {
                        dailyDealHistories.pop();
                        dailyDealHistories.push(data.oneDayAgoDailyDealHistory);
                        dailyDealHistories.push(data.lastDailyDealHistory);
                    } else {
                        data.deltaDailyDealHistories.forEach(item => {
                            dailyDealHistories.push(item);
                        });
                    }

                    //update candleStick setting values
                    itemName = data.itemName;
                    companyName = data.companyName;
                    $('input[id=inputCompanyName]').attr('value',companyName);
                    startDate = data.startDate;
                    $('input[id=inputStartDate]').attr('value',startDate);
                    endDate = data.endDate;
                    $('input[id=inputEndDate]').attr('value',endDate);
                    initialPortion= data.initialPortion;

                    if(isEmptyJsonArray(data.deltaDailyDealHistories)) {
                        candleStickDataList.pop();
                        candleStickDataList.push([data.oneDayAgoDailyDealHistory.dealDateForTimestamp, data.oneDayAgoDailyDealHistory.startPrice, data.oneDayAgoDailyDealHistory.highPrice, data.oneDayAgoDailyDealHistory.lowPrice, data.oneDayAgoDailyDealHistory.closingPrice]);
                        candleStickDataList.push([data.lastDailyDealHistory.dealDateForTimestamp, data.lastDailyDealHistory.startPrice, data.lastDailyDealHistory.highPrice, data.lastDailyDealHistory.lowPrice, data.lastDailyDealHistory.closingPrice]);
                    } else {
                        data.deltaDailyDealHistories.forEach(item => {
                            candleStickDataList.push([
                                item.dealDateForTimestamp,
                                item.startPrice,
                                item.highPrice,
                                item.lowPrice,
                                item.closingPrice
                            ]);
                        });
                    }

                    if(isEmptyJsonArray(data.deltaDailyDealHistories)) {
                        volumeList.pop();
                        volumeList.push([data.oneDayAgoDailyDealHistory.dealDateForTimestamp, data.oneDayAgoDailyDealHistory.tradeVolume]);
                        volumeList.push([data.lastDailyDealHistory.dealDateForTimestamp, data.lastDailyDealHistory.tradeVolume]);
                    } else {
                        data.deltaDailyDealHistories.forEach(item => {
                            volumeList.push([
                                item.dealDateForTimestamp,
                                item.tradeVolume
                            ]);
                        });
                    }

                    if(isEmptyJsonArray(data.deltaDailyDealHistories)) {
                        portionList.pop();
                        portionList.push([data.oneDayAgoDailyDealHistory.dealDateForTimestamp, Number(data.oneDayAgoDailyDealHistory.portion.toFixed(0))]);
                        portionList.push([data.lastDailyDealHistory.dealDateForTimestamp, Number(data.lastDailyDealHistory.portion.toFixed(0))]);
                    } else {
                        data.deltaDailyDealHistories.forEach(item => {
                            portionList.push([
                                item.dealDateForTimestamp,
                                Number(item.portion.toFixed(0))
                            ]);
                        });
                    }

                    if( data.oneDayAgoDailyDealHistory.myAverageUnitPrice !== 0 ) {
                        if(isEmptyJsonArray(data.deltaDailyDealHistories)) {
                            myAverageUnitPriceList.pop();
                            myAverageUnitPriceList.push([data.oneDayAgoDailyDealHistory.dealDateForTimestamp, data.oneDayAgoDailyDealHistory.myAverageUnitPrice]);
                            myAverageUnitPriceList.push([data.lastDailyDealHistory.dealDateForTimestamp, data.lastDailyDealHistory.myAverageUnitPrice]);
                        } else {
                            data.deltaDailyDealHistories.forEach(item => {
                                myAverageUnitPriceList.push([
                                    item.dealDateForTimestamp,
                                    item.myAverageUnitPrice
                                ]);
                            });
                        }
                    }
                    if( data.oneDayAgoDailyDealHistory.additionalBuyingQuantity !== 0 ) {
                        if(isEmptyJsonArray(data.deltaDailyDealHistories)) {
                            additionalBuyingPrice.pop();
                            additionalBuyingPrice.push([data.oneDayAgoDailyDealHistory.dealDateForTimestamp, data.oneDayAgoDailyDealHistory.additionalBuyingAmount / data.oneDayAgoDailyDealHistory.additionalBuyingQuantity]);
                            additionalBuyingPrice.push([data.lastDailyDealHistory.dealDateForTimestamp, data.lastDailyDealHistory.additionalBuyingAmount / data.lastDailyDealHistory.additionalBuyingQuantity]);
                        } else {
                            data.deltaDailyDealHistories.forEach(item => {
                                additionalBuyingPrice.push([
                                    item.dealDateForTimestamp,
                                    item.additionalBuyingAmount / item.additionalBuyingQuantity
                                ]);
                            });
                        }

                        if(isEmptyJsonArray(data.deltaDailyDealHistories)) {
                            additionalBuyingAmount.pop();
                            additionalBuyingAmount.push([data.oneDayAgoDailyDealHistory.dealDateForTimestamp, data.oneDayAgoDailyDealHistory.additionalBuyingAmount]);
                            additionalBuyingAmount.push([data.lastDailyDealHistory.dealDateForTimestamp, data.lastDailyDealHistory.additionalBuyingAmount]);
                        } else {
                            data.deltaDailyDealHistories.forEach(item => {
                                additionalBuyingAmount.push([
                                    item.dealDateForTimestamp,
                                    item.additionalBuyingAmount
                                ]);
                            });
                        }
                    }
                    if( data.oneDayAgoDailyDealHistory.additionalSellingQuantity !== 0 ) {
                        if(isEmptyJsonArray(data.deltaDailyDealHistories)) {
                            additionalSellingPrice.pop();
                            additionalSellingPrice.push([data.oneDayAgoDailyDealHistory.dealDateForTimestamp, data.oneDayAgoDailyDealHistory.additionalSellingAmount / data.oneDayAgoDailyDealHistory.additionalSellingQuantity]);
                            additionalSellingPrice.push([data.lastDailyDealHistory.dealDateForTimestamp, data.lastDailyDealHistory.additionalSellingAmount / data.lastDailyDealHistory.additionalSellingQuantity]);
                        } else {
                            data.deltaDailyDealHistories.forEach(item => {
                                additionalSellingPrice.push([
                                    item.dealDateForTimestamp,
                                    item.additionalSellingAmount / item.additionalSellingQuantity
                                ]);
                            });
                        }

                        if(isEmptyJsonArray(data.deltaDailyDealHistories)) {
                            additionalSellingAmount.pop();
                            additionalSellingAmount.push([data.oneDayAgoDailyDealHistory.dealDateForTimestamp, data.oneDayAgoDailyDealHistory.additionalSellingAmount]);
                            additionalSellingAmount.push([data.lastDailyDealHistory.dealDateForTimestamp, data.lastDailyDealHistory.additionalSellingAmount]);
                        } else {
                            data.deltaDailyDealHistories.forEach(item => {
                                additionalSellingAmount.push([
                                    item.dealDateForTimestamp,
                                    item.additionalSellingAmount
                                ]);
                            });
                        }
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
        }

        function isEmptyJsonArray(arr) {
            return Array.isArray(arr) && arr.length === 0;
        }

        function copyChart(){
            var url = "/deal-training-copy";
            var copyTarget = {
                nextTryDate: nextTryDate,
                currentClosingPrice: currentClosingPrice,
                dealModifications: dealModifications,
                dailyDealHistories: dailyDealHistories,
                itemName: itemName,
                companyName: companyName,
                startDate: startDate,
                endDate: endDate,
                initialPortion: initialPortion,
                candleStickDataList: [],
                volumeList: [],
                portionList: [],
                myAverageUnitPriceList: [],
                additionalBuyingPrice: [],
                additionalSellingPrice: [],
                additionalBuyingAmount: [],
                additionalSellingAmount: [],
                groupingUnits: groupingUnits,
                earningRate: earningRate,
                earningAmount: earningAmount,
                slotAmount: slotAmount,
                portion: portion,
                remainingSlotAmount: remainingSlotAmount,
                remainingPortion: remainingPortion,
                sumOfPurchaseAmount: sumOfPurchaseAmount,
                sumOfSellingAmount: sumOfSellingAmount,
                sumOfPurchaseQuantity: sumOfPurchaseQuantity,
                sumOfSellingQuantity: sumOfSellingQuantity,
                sumOfCommission: sumOfCommission,
                totalAmount: totalAmount,
                valuationPercent: valuationPercent,
                averageUnitPrice: averageUnitPrice,
                isError: isError,
                errorMessage: errorMessage
            };

            // 데이터를 웹 스토리지에 저장
            sessionStorage.setItem('copyData', JSON.stringify(copyTarget));
            // 새로운 탭으로 JSP 페이지 열기
            window.open(url, '_blank');
        }

        function validatePrices(sellPrice, buyPrice){
            var lastDailyDealHistory = dailyDealHistories[dailyDealHistories.length - 1];
            var lastHighPrice = lastDailyDealHistory.highPrice;
            var lastLowPrice = lastDailyDealHistory.lowPrice;
            if(sellPrice > lastHighPrice){
                alert('매도금액이 고가를 넘어설 수 없습니다.');
                return false;
            }
            if(sellPrice < lastLowPrice){
                alert('매도금액이 저가보다 낮을 수 없습니다.');
                return false;
            }
            if(buyPrice > lastHighPrice){
                alert('매수금액이 고가를 넘어설 수 없습니다.');
                return false;
            }
            if(buyPrice < lastLowPrice){
                alert('매수금액이 저가보다 낮을 수 없습니다.');
                return false;
            }

            return true;
        }

        function confirmPercents(sellPercent, buyPercent){
            if(sellPercent[0] > 100){
                return confirm('매도비중이 100%를 넘어갑니다. 그래도 계속 진행하시겠습니까?');
            }
            if(buyPercent[0] > 100){
                return confirm('매수비중이 100%를 넘어갑니다. 그래도 계속 진행하시겠습니까?');
            }

            return true;
        }

        function validateJumpDate(sellPercent, buyPercent, jumpDate) {
            if((sellPercent[0] > 0 || buyPercent[0] > 0) && jumpDate !== ""){
                alert('날짜를 한번에 넘어갈 때에는 매수/매도를 할 수 없습니다.');
                return false;
            }
            if(jumpDate !== "") {
                console.log('nextTryDate' + nextTryDate);
                if(compareDates(nextTryDate, jumpDate) >= 0) {
                    alert('날짜를 한번에 넘어갈 때에는 미래로만 갈 수 있습니다');
                    return false;
                };
            }
            return true;
        }

        function compareDates(date1, date2) {
            var [year1, month1, day1] = date1.split('-').map(Number);
            var [year2, month2, day2] = date2.split('-').map(Number);

            if (year1 !== year2) return year1 - year2;
            if (month1 !== month2) return month1 - month2;
            return day1 - day2;
        }

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
                };
            }

            document.addEventListener('DOMContentLoaded', drawChart());
        }

        function createToggleButton() {
            const toggleContainerId = 'toggle-button-container';
            let toggleButtonContainer = $('#' + toggleContainerId);

            if (toggleButtonContainer.length === 0) {
                toggleButtonContainer = $('<div>', {
                    id: toggleContainerId,
                    class: 'd-flex justify-content-end mb-3'
                });

                const toggleButton = $('<button>', {
                    id: 'candlestick-heikinashi-toggle',
                    class: 'btn btn-outline-primary btn-sm',
                    html: '<i class="fas fa-exchange-alt"></i> 캔들스틱/하이킨아시 전환'
                });

                toggleButtonContainer.append(toggleButton);
                toggleButtonContainer.insertBefore('#container');
            }
        }

        function drawChart() {
            if (chartInstance) {
                chartInstance.destroy();
            }

            createToggleButton();

            // 이동평균 계산
            const fiveMA = calculateMovingAverage(candleStickDataList, 5);
            const twentyMA = calculateMovingAverage(candleStickDataList, 20);
            const sixtyMA = calculateMovingAverage(candleStickDataList, 60);
            const oneTwentyMA = calculateMovingAverage(candleStickDataList, 120);

            // 데이터 로딩 함수 재정의
            Highcharts.ajax = function(attr) {
                console.log('데이터 로딩 시도가 차단되었습니다.');
                if (attr.error) attr.error();
            };

            // 날짜 형식 변환 함수
            function formatDate(dateString) {
                const date = new Date(dateString);
                return date.getFullYear() + '년 ' + (date.getMonth() + 1) + '월 ' + date.getDate() + '일';
            }

            const formattedStartDate = formatDate(startDate);
            const formattedEndDate = formatDate(endDate);

            const highchartsOptions = Highcharts.setOptions({
                    lang: {
                        loading: '로딩중입니다...',
                        months: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
                        weekdays: ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'],
                        shortMonths: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
                        exportButtonTitle: "Export",
                        printButtonTitle: "프린트",
                        rangeSelectorFrom: "시작",
                        rangeSelectorTo: "끝",
                        rangeSelectorZoom: "기간",
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

            chartInstance = Highcharts.stockChart('container', {
                title: {
                    text: '주식 차트(' + itemName + ')',
                    style: {
                        color: '#00443a',
                        fontSize: '28px',
                        fontWeight: 'bold'
                    }
                },
                subtitle: {
                    text: '시뮬레이션 기간: ' + formattedStartDate + ' - ' + formattedEndDate,
                    align: 'left',
                    style: {
                        fontSize: '16px'
                    }
                },
                chart: {
                    zoomType: 'x',
                    events: {
                        load: function() {
                            this.showLoading = function() {};
                            this.hideLoading = function() {};
                        }
                    }
                },
                time: {
                    useUTC: false,
                    timezone: 'Asia/Seoul',
                },
                legend: {
                    enabled: true,
                    align: 'center',
                    backgroundColor: '#FCFFC5',
                    borderColor: 'black',
                    borderWidth: 2,
                    shadow: true,
                    itemStyle: {
                        fontSize: '14px'
                    }
                },
                rangeSelector: {
                    buttons: [{
                        type: 'year',
                        count: 1,
                        text: '일봉',
                        dataGrouping: {
                            forced: true,
                            units: [['day', [1]]]
                        }
                    }, {
                        type: 'year',
                        count: 1,
                        text: '주봉',
                        dataGrouping: {
                            forced: true,
                            units: [['week', [1]]]
                        }
                    }, {
                        type: 'year',
                        count: 1,
                        text: '월봉',
                        dataGrouping: {
                            forced: true,
                            units: [['month', [1]]]
                        }
                    }, {
                        type: 'all',
                        text: '전체',
                        dataGrouping: {
                            forced: true,
                            units: [['month', [1]]]
                        }
                    }],
                    selected: 0,
                    inputEnabled: false
                },
                navigator: {
                    enabled: true,
                    height: 50,
                    series: {
                        type: 'line',
                        color: Highcharts.getOptions().colors[0]
                    },
                    xAxis: {
                        events: {
                            afterSetExtremes: function(e) {
                                // Navigator의 범위가 변경될 때 버튼 상태 업데이트
                                updateButtonState(e.min, e.max);
                            }
                        }
                    }
                },
                scrollbar: {
                    enabled: true
                },
                yAxis: [{
                    labels: {
                        align: 'right',
                        x: -3,
                        style: {
                            fontSize: '14px'
                        },
                        formatter: function() {
                            return Highcharts.numberFormat(this.value, 0, '', ',');
                        }
                    },
                    title: {
                        align: 'high',
                        offset: 0,
                        rotation: 0,
                        y: -10,
                        x: -20,
                        reserveSpace: false,
                        text: '가격',
                        style: {
                            fontSize: '16px'
                        }
                    },
                    height: '55%',
                    lineWidth: 2,
                    resize: {
                        enabled: true
                    }
                }, {
                    labels: {
                        align: 'right',
                        x: -3,
                        style: {
                            fontSize: '14px'
                        }
                    },
                    title: {
                        align: 'high',
                        offset: 0,
                        rotation: 0,
                        y: -10,
                        x: -30,
                        reserveSpace: false,
                        text: '거래량',
                        style: {
                            fontSize: '16px'
                        }
                    },
                    top: '60%',
                    height: '10%',
                    offset: 0,
                    lineWidth: 2
                }, {
                    labels: {
                        align: 'right',
                        x: -3,
                        style: {
                            fontSize: '14px'
                        }
                    },
                    title: {
                        align: 'high',
                        offset: 0,
                        rotation: 0,
                        y: -10,
                        x: -20,
                        reserveSpace: false,
                        text: '비중',
                        style: {
                            fontSize: '16px'
                        }
                    },
                    top: '75%',
                    height: '10%',
                    offset: 0,
                    lineWidth: 2
                }, {
                    labels: {
                        align: 'right',
                        x: -3,
                        style: {
                            fontSize: '14px'
                        }
                    },
                    title: {
                        align: 'high',
                        textAlign: 'left',
                        offset: 0,
                        rotation: 0,
                        y: -10,
                        x: -60,
                        reserveSpace: false,
                        text: '매수/매도 금액',
                        style: {
                            fontSize: '16px'
                        }
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
                    },
                    series: {
                        dataGrouping: {
                            enabled: true,
                            forced: true,
                            units: [['day', [1]]],
                            groupPixelWidth: 2
                        }
                    }
                },
                tooltip: {
                    split: true,
                    style: {
                        fontSize: '14px'
                    },
                    formatter: function() {
                        const points = this.points;
                        // UTC+9 (한국 시간)으로 조정
                        const date = new Date(this.x + 9 * 3600 * 1000);
                        let tooltipText = '<span style="font-size: 14px">' + Highcharts.dateFormat('%Y년 %m월 %d일', date) + '</span><br/>';

                        points.forEach(function(point) {
                            if (point.series.name === itemName) {
                                const open = Highcharts.numberFormat(point.point.open, 0, '', ',');
                                const high = Highcharts.numberFormat(point.point.high, 0, '', ',');
                                const low = Highcharts.numberFormat(point.point.low, 0, '', ',');
                                const close = Highcharts.numberFormat(point.point.close, 0, '', ',');

                                tooltipText += '<br/><span style="color: ' + point.color + '">●</span> ' + point.series.name + ':<br/>' +
                                    '시가: ' + open + '<br/>' +
                                    '고가: ' + high + '<br/>' +
                                    '저가: ' + low + '<br/>' +
                                    '종가: ' + close + '<br/>';

                                // 변화율 계산 및 추가
                                const prevClose = point.point.prev ? point.point.prev.close : point.point.open;
                                const changeRate = ((point.point.close - prevClose) / prevClose * 100).toFixed(2);
                                const changeRateColor = changeRate >= 0 ? 'red' : 'blue';
                                tooltipText += '변화율: <span style="color: ' + changeRateColor + '">' + changeRate + '%</span><br/>';
                            } else if (point.series.name.includes('이동평균')) {
                                tooltipText += '<br/><span style="color: ' + point.color + '">●</span> ' + point.series.name + ': ' +
                                    Highcharts.numberFormat(point.y, 0, '', ',') + '<br/>';
                            } else {
                                tooltipText += '<br/><span style="color: ' + point.color + '">●</span> ' + point.series.name + ': ' +
                                    Highcharts.numberFormat(point.y, 2, '.', ',') + '<br/>';
                            }
                        });

                        return tooltipText;
                    }
                },
                series: [{
                    id: 'candle',
                    name: itemName,
                    type: 'candlestick',
                    data: candleStickDataList,
                    dataGrouping: {
                        units: [
                            ['day', [1]],
                            ['week', [1]],
                            ['month', [1]]
                        ]
                    }
                }, {
                    type: 'column',
                    name: '거래량',
                    data: volumeList,
                    yAxis: 1,
                    dataGrouping: {
                        units: [
                            ['day', [1]],
                            ['week', [1]],
                            ['month', [1]]
                        ]
                    }
                }, {
                    type: 'spline',
                    name: '평균단가',
                    data: myAverageUnitPriceList,
                    dataGrouping: {
                        units: [
                            ['day', [1]],
                            ['week', [1]],
                            ['month', [1]]
                        ]
                    },
                    color: '#b4aa36',
                    lineWidth: 4,
                    onSeries: 'candle'
                }, {
                    type: 'scatter',
                    name: '추가 매수단가',
                    data: additionalBuyingPrice,
                    dataGrouping: {
                        units: [
                            ['day', [1]],
                            ['week', [1]],
                            ['month', [1]]
                        ]
                    },
                    color: '#afa400',
                    onSeries: 'candle',
                    marker: {
                        symbol: 'triangle',
                        fillColor: 'red',
                        lineWidth: 2,
                        lineColor: 'white',
                        radius: 6
                    }
                }, {
                    type: 'scatter',
                    name: '추가 매도단가',
                    data: additionalSellingPrice,
                    dataGrouping: {
                        units: [
                            ['day', [1]],
                            ['week', [1]],
                            ['month', [1]]
                        ]
                    },
                    color: '#007eff',
                    onSeries: 'candle',
                    marker: {
                        symbol: 'triangle-down',
                        fillColor: 'blue',
                        lineWidth: 2,
                        lineColor: 'white',
                        radius: 6
                    }
                }, {
                    type: 'spline',
                    name: '5일 이동평균',
                    data: fiveMA,
                    dataGrouping: {
                        units: [
                            ['day', [1]],
                            ['week', [1]],
                            ['month', [1]]
                        ]
                    },
                    color: '#383832',
                    lineWidth: 1,
                    onSeries: 'candle'
                }, {
                    type: 'spline',
                    name: '20일 이동평균',
                    data: twentyMA,
                    dataGrouping: {
                        units: [
                            ['day', [1]],
                            ['week', [1]],
                            ['month', [1]]
                        ]
                    },
                    color: '#ff0000',
                    lineWidth: 1,
                    onSeries: 'candle'
                }, {
                    type: 'spline',
                    name: '60일 이동평균',
                    data: sixtyMA,
                    dataGrouping: {
                        units: [
                            ['day', [1]],
                            ['week', [1]],
                            ['month', [1]]
                        ]
                    },
                    color: '#514fff',
                    lineWidth: 1,
                    onSeries: 'candle'
                }, {
                    type: 'spline',
                    name: '120일 이동평균',
                    data: oneTwentyMA,
                    dataGrouping: {
                        units: [
                            ['day', [1]],
                            ['week', [1]],
                            ['month', [1]]
                        ]
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
                        units: [
                            ['day', [1]],
                            ['week', [1]],
                            ['month', [1]]
                        ]
                    },
                    dataLabels: {
                        enabled: true,
                        formatter: function() {
                            return Highcharts.numberFormat(this.y, 1) + '%';
                        },
                        style: {
                            fontSize: '12px',
                            fontWeight: 'bold'
                        },
                        y: -10, // 라벨을 선 위에 표시
                        step: 20 // 20개의 데이터 포인트마다 라벨 표시
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
                        units: [
                            ['day', [1]],
                            ['week', [1]],
                            ['month', [1]]
                        ]
                    },
                    yAxis: 3,
                    color: '#FF0000'
                }, {
                    type: 'column',
                    name: '매도 금액',
                    data: additionalSellingAmount,
                    dataGrouping: {
                        units: [
                            ['day', [1]],
                            ['week', [1]],
                            ['month', [1]]
                        ]
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

            // 초기 데이터 그룹화 설정 (일봉으로 시작)
            updateDataGrouping('day');

            // 초기 상태를 일봉, 1년으로 설정
            const extremes = chartInstance.xAxis[0].getExtremes();
            const dataMax = extremes.dataMax;
            const dataMin = extremes.dataMin;
            const ONE_YEAR = 365 * 24 * 60 * 60 * 1000;
            const newMin = Math.max(dataMax - ONE_YEAR, dataMin);

            chartInstance.xAxis[0].setExtremes(newMin, dataMax);
            updateButtonState(newMin, dataMax);
        }

        function convertToCSV(objArray) {
            const array = typeof objArray !== 'object' ? JSON.parse(objArray) : objArray;
            let str = '';

            // 헤더 추가
            const headers = Object.keys(array[0]);
            str += headers.join(',') + '\r\n';

            // 데이터 추가
            for (let i = 0; i < array.length; i++) {
                let line = '';
                for (let index in headers) {
                    if (line !== '') line += ',';
                    let value = array[i][headers[index]];
                    // 값에 쉼표가 포함되어 있다면 큰따옴표로 묶습니다
                    line += value !== null && value !== undefined ?
                        value.toString().indexOf(',') !== -1 ? `"${value}"` : value : '';
                }
                str += line + '\r\n';
            }

            return str;
        }

        function downloadCSV(data, filename) {
            const blob = new Blob([data], { type: 'text/csv;charset=utf-8;' });
            const url = URL.createObjectURL(blob);

            const link = document.createElement("a");
            link.style.display = "none";
            link.href = url;
            link.download = filename;

            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);

            // URL 객체를 해제합니다.
            setTimeout(() => {
                URL.revokeObjectURL(url);
            }, 100);
        }

        // 하이킨아시 데이터 계산 함수
        function calculateHeikinAshi(data) {
            return data.reduce((acc, point, index) => {
                const haPoint = [point[0]];  // 시간은 그대로
                if (index === 0) {
                    haPoint.push(point[1], point[2], point[3], point[4]);  // 시가, 고가, 저가, 종가
                } else {
                    const prevHa = acc[index - 1];
                    const open = (prevHa[1] + prevHa[4]) / 2;
                    const close = (point[1] + point[2] + point[3] + point[4]) / 4;
                    haPoint.push(
                        open,
                        Math.max(point[2], open, close),
                        Math.min(point[3], open, close),
                        close
                    );
                }
                acc.push(haPoint);
                return acc;
            }, []);
        }

        //이동평균선 계산함수
        function calculateMovingAverage(data, period) {
            return data.map((_, index, array) => {
                if (index < period - 1) {
                    return [array[index][0], null];
                }
                const slice = array.slice(index - period + 1, index + 1);
                const sum = slice.reduce((acc, val) => acc + val[4], 0);
                return [array[index][0], sum / period];
            });
        }

        function updateDataGrouping(unit) {
            if (chartInstance) {
                const extremes = chartInstance.xAxis[0].getExtremes();
                const dataMax = extremes.dataMax;
                const dataMin = extremes.dataMin;

                const ONE_YEAR = 365 * 24 * 60 * 60 * 1000; // 1년을 밀리초로 표현
                const newMin = Math.max(dataMax - ONE_YEAR, dataMin);

                chartInstance.series.forEach(function(series) {
                    series.update({
                        dataGrouping: {
                            forced: true,
                            units: [[unit, [1]]]
                        }
                    }, false);
                });

                // Navigator와 메인 차트의 범위를 1년으로 설정
                chartInstance.xAxis[0].setExtremes(newMin, dataMax);

                chartInstance.redraw();
            }
        }

        // 버튼 상태 업데이트 함수
        function updateButtonState(min, max) {
            const buttons = chartInstance.rangeSelector.buttons;
            const range = max - min;
            const ONE_YEAR = 365 * 24 * 60 * 60 * 1000;

            buttons.forEach((button, index) => {
                if (index < 3 && Math.abs(range - ONE_YEAR) < 24 * 60 * 60 * 1000) {
                    // 1년 범위일 때 해당 버튼 활성화
                    button.setState(2);
                } else if (index === 3 && range === chartInstance.xAxis[0].max - chartInstance.xAxis[0].min) {
                    // 전체 범위일 때 '전체' 버튼 활성화
                    button.setState(2);
                } else {
                    button.setState(0);
                }
            });
        }
    </script>
</head>
<body>
<!-- Navbar -->
<nav class="navbar navbar-expand-lg fixed-top navbar-dark">
    <div class="container">
        <a class="navbar-brand" href="/">주식 매매 시뮬레이션</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="btn-home" href="/">
                        <i class="fas fa-home"></i>
                        <span>초기화면</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="btn-auto-sim" href="/buildup">자동 시뮬레이션으로 이동</a>
                </li>
                <li class="nav-item">
                    <% if (session.getAttribute("sessionUser") == null) { %>
                    <button class="btn btn-outline-light" onclick="location.href='/login/google'">Google로 로그인</button>
                    <% } else { %>
                    <button class="btn btn-outline-light me-2" onclick="location.href='/logout/google'">로그아웃</button>
                    <button class="btn btn-info" onclick="openDealHistoryTab()">시뮬레이션 이어하기</button>
                    <% } %>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- 메인 컨테이너 -->
<div class="container main-container">
    <form id="modifyCalculation" name="calculateRequestFrom">
        <h2 class="mb-4"><strong>매매 시뮬레이션</strong>
            <i class="fas fa-question-circle tooltip-icon"
               data-bs-toggle="tooltip"
               data-bs-placement="right"
               title="그래프를 보고 매수/매도/다음을 선택하세요. 매수/매도 시 단가와 비중을 입력하세요. 매도 시 0.3% 수수료가 적용됩니다."></i>
        </h2>

        <div></div>
        <div class="mb-4" id= "modifyInputGroup">
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
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon6">한번에 이동할 날짜</span>
                <input id="jumpDate" type="date" class="form-control" name="jumpDate" aria-label="jumpDate" aria-describedby="basic-addon6">
            </div>
        </div>

        <div class="d-grid gap-2 d-sm-flex justify-content-sm-center mb-4">
            <button type="button" id="submitButton" class="btn btn-primary btn-lg px-4 gap-3">다음</button>
        </div>

        <div class="d-grid gap-2 d-sm-flex justify-content-sm-end mb-4">
            <button type="button" id="copyButton" class="btn btn-dark btn-lg px-4 gap-3">시뮬레이션 복제</button>
        </div>

        <hr class="my-4" style="border-color: #3498db;">

        <div id="container" style="height: 1000px; min-width: 310px" class="mb-4"></div>

        <hr class="my-4" style="border-color: #3498db;">

        <div class="mb-4">
            <div id="dealError" class="alert alert-danger" style="display: none;">
                <h4 class="alert-heading">오류 발생</h4>
                <p class="errorMessage mb-0"></p>
            </div>
            <div id="dealStatus" class="card">
                <div class="card-header bg-primary text-white">
                    <h2 class="mb-0"><strong>매매 현황</strong></h2>
                </div>
                <div class="card-body">
                    <div id="dealStatusWithEmptyEarningRate" style="display: none;">
                        <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
                            <div class="col"><div class="status-item"><span class="status-label">실현수익률:</span> <span class="status-value">0%</span></div></div>
                            <div class="col"><div class="status-item"><span class="status-label">실현손익:</span> <span class="status-value">0원</span></div></div>
                            <div class="col"><div class="status-item"><span class="status-label">슬랏 할당금액:</span> <span class="status-value slotAmount"></span></div></div>
                            <div class="col"><div class="status-item"><span class="status-label">현재 비중:</span> <span class="status-value portion"></span></div></div>
                            <div class="col"><div class="status-item"><span class="status-label">슬랏 예수금:</span> <span class="status-value remainingSlotAmount"></span></div></div>
                            <div class="col"><div class="status-item"><span class="status-label">슬랏 예수금 비중:</span> <span class="status-value remainingPortion"></span></div></div>
                            <div class="col"><div class="status-item"><span class="status-label">총 매입금액:</span> <span class="status-value sumOfPurchaseAmount"></span></div></div>
                            <div class="col"><div class="status-item"><span class="status-label">총 매도금액:</span> <span class="status-value">0원</span></div></div>
                            <div class="col"><div class="status-item"><span class="status-label">총 매입수량:</span> <span class="status-value sumOfPurchaseQuantity"></span></div></div>
                            <div class="col"><div class="status-item"><span class="status-label">총 매도수량:</span> <span class="status-value">0주</span></div></div>
                            <div class="col"><div class="status-item"><span class="status-label">총 매도수수료(0.3%):</span> <span class="status-value">0원</span></div></div>
                            <div class="col"><div class="status-item"><span class="status-label">현재 평가금액:</span> <span class="status-value totalAmount"></span></div></div>
                            <div class="col"><div class="status-item"><span class="status-label">현재 평가손익:</span> <span class="status-value valuationPercent"></span></div></div>
                            <div class="col"><div class="status-item"><span class="status-label">현재 평균단가:</span> <span class="status-value averageUnitPrice"></span></div></div>
                            <div class="col"><div class="status-item"><span class="status-label">현재 종가:</span> <span class="status-value currentClosingPrice"></span></div></div>
                        </div>
                    </div>
                    <div id="dealStatusWithEarningRate" style="display: none;">
                        <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
                            <div class="col"><div class="status-item"><span class="status-label">실현수익률:</span> <span class="status-value earningRate"></span></div></div>
                            <div class="col"><div class="status-item"><span class="status-label">실현손익:</span> <span class="status-value earningAmount"></span></div></div>
                            <div class="col"><div class="status-item"><span class="status-label">슬랏 할당금액:</span> <span class="status-value slotAmount"></span></div></div>
                            <div class="col"><div class="status-item"><span class="status-label">현재 비중:</span> <span class="status-value portion"></span></div></div>
                            <div class="col"><div class="status-item"><span class="status-label">슬랏 예수금:</span> <span class="status-value remainingSlotAmount"></span></div></div>
                            <div class="col"><div class="status-item"><span class="status-label">슬랏 예수금 비중:</span> <span class="status-value remainingPortion"></span></div></div>
                            <div class="col"><div class="status-item"><span class="status-label">총 매입금액:</span> <span class="status-value sumOfPurchaseAmount"></span></div></div>
                            <div class="col"><div class="status-item"><span class="status-label">총 매도금액:</span> <span class="status-value sumOfSellingAmount"></span></div></div>
                            <div class="col"><div class="status-item"><span class="status-label">총 매입수량:</span> <span class="status-value sumOfPurchaseQuantity"></span></div></div>
                            <div class="col"><div class="status-item"><span class="status-label">총 매도수량:</span> <span class="status-value sumOfSellingQuantity"></span></div></div>
                            <div class="col"><div class="status-item"><span class="status-label">총 매도수수료(0.3%):</span> <span class="status-value sumOfCommission"></span></div></div>
                            <div class="col"><div class="status-item"><span class="status-label">현재 평가금액:</span> <span class="status-value totalAmount"></span></div></div>
                            <div class="col"><div class="status-item"><span class="status-label">현재 평가손익:</span> <span class="status-value valuationPercent"></span></div></div>
                            <div class="col"><div class="status-item"><span class="status-label">현재 평균단가:</span> <span class="status-value averageUnitPrice"></span></div></div>
                            <div class="col"><div class="status-item"><span class="status-label">현재 종가:</span> <span class="status-value currentClosingPrice"></span></div></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <hr class="my-4" style="border-color: #3498db;">


        <div class="mb-4">
            <h2 class="mb-3"><strong>최초 입력 요청값</strong></h2>
            <div class="px-4 py-5 my-5 text-left">
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
        </div>

        <hr class="my-4" style="border-color: #3498db;">
        <div class="mb-4">
            <h2 class="mb-3"><strong>추가 매수/매도 이력</strong></h2>
            <div id="additionalBuyingSellHistory">
            </div>
        </div>
    </form>

    <hr class="my-4" style="border-color: #3498db;">

    <div class="download-section">
        <h4>매매 내역 다운로드</h4>
        <button id="downloadHistoriesBtn" class="btn-download">
            <i class="fas fa-download"></i> CSV 다운로드
        </button>
    </div>
</div>
<!-- Footer -->
<footer class="text-center text-lg-start bg-black text-white">
    <div class="container py-4">
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <div class="aqr-qr-container mb-4">
                    <div id="aqr-widget-area" class="mb-3"></div>
                    <div class="qr-code-wrapper">
                        <img src="resources/images/aqrCode.png" alt="개발자 기부 QR 코드" class="img-fluid" style="max-width: 150px;">
                        <p class="mt-2 mb-0">기부 QR 코드</p>
                    </div>
                </div>
                <div class="contact-info">
                    <p class="mb-2">개발자(펭수르) 개별 문의처: <a class="text-reset fw-bold" href="mailto:dragon1086@naver.com">dragon1086@naver.com</a></p>
                    <p class="mb-0">주식 데이터 구매 문의처: <a class="text-reset fw-bold" href="https://kmong.com/gig/245871" target="_blank">https://kmong.com/gig/245871</a></p>
                </div>
            </div>
        </div>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/feather-icons@4.28.0/dist/feather.min.js" integrity="sha384-uO3SXW5IuS1ZpFPKugNNWqTZRRglnUJK6UAZ/gxOX80nxEkN9NcGZTftn6RzhGWE" crossorigin="anonymous"></script>
<script src="https://code.highcharts.com/stock/highstock.js"></script>
<script src="https://code.highcharts.com/stock/modules/exporting.js"></script>
<script src="https://aq.gy/c/widget.js"></script>
<script>
    new AQRWidget().renderAQRWidget(
        {
            token : "3iMB^", // uniq token
            layer_id : "aqr-widget-area", // target layer id
            profile : false, // Show or Not profile image and account name
            libbutton : true, // Show or Not SNS Link Buttons
            bgcolor : "#000000", // hex only
            textcolor : "#ffffff", // hex only
            button_text : "서버 운영비 기부하기"
        }
    );
</script>

</body>
</html>