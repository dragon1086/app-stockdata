// sessionStorage에서 데이터 읽어오기
var copyData = JSON.parse(sessionStorage.getItem('copyData'));
//result setting values
var nextTryDate = copyData.nextTryDate;
var currentClosingPrice = copyData.currentClosingPrice;
var dealModifications = copyData.dealModifications;
var dailyDealHistories = copyData.dailyDealHistories;

//candleStick setting values
var itemName = copyData.itemName;
var companyName = copyData.companyName;
var startDate = copyData.startDate;
var endDate = copyData.endDate;
var initialPortion = copyData.initialPortion;
var candleStickDataList = copyData.candleStickDataList;
var volumeList = copyData.volumeList;
var portionList = copyData.portionList;
var myAverageUnitPriceList = copyData.myAverageUnitPriceList;
var additionalBuyingPrice = copyData.additionalBuyingPrice;
var additionalSellingPrice = copyData.additionalSellingPrice;
var additionalBuyingAmount = copyData.additionalBuyingAmount;
var additionalSellingAmount = copyData.additionalSellingAmount;
var groupingUnits = copyData.groupingUnits;
var earningRate = copyData.earningRate;
var earningAmount = copyData.earningAmount;
var slotAmount = copyData.slotAmount;
var portion = copyData.portion;
var remainingSlotAmount = copyData.remainingSlotAmount;
var remainingPortion = copyData.remainingPortion;
var sumOfPurchaseAmount = copyData.sumOfPurchaseAmount;
var sumOfSellingAmount = copyData.sumOfSellingAmount;
var sumOfPurchaseQuantity = copyData.sumOfPurchaseQuantity;
var sumOfSellingQuantity = copyData.sumOfSellingQuantity;
var sumOfCommission = copyData.sumOfCommission;
var totalAmount = copyData.totalAmount;
var valuationPercent = copyData.valuationPercent;
var averageUnitPrice = copyData.averageUnitPrice;
var historyId = null;

//etc
var isError = copyData.isError;
var errorMessage = copyData.errorMessage;

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

        isHeikinAshi = !isHeikinAshi; //상태 토글

        const candleSeries = chartInstance.get('candle');
        if (!candleSeries) {
            console.error('Candle series not found');
            return false;
        }

        candleSeries.update({
            name: itemName + (isHeikinAshi ? ' (하이킨아시)' : ''),
            data: isHeikinAshi ? calculateHeikinAshi(candleStickDataList) : candleStickDataList
        }, true); // true를 넣어 즉시 리드로우

        return false;
    });
    document.cookie = "SameSite=None; Secure";

    // 창 크기 변경과 화면 회전 처리를 위한 변수
    let resizeTimer;
    let lastOrientation = window.orientation;

    // 화면 회전과 리사이즈 통합 처리
    window.addEventListener('resize', function() {
        clearTimeout(resizeTimer);
        resizeTimer = setTimeout(function() {
            if (chartInstance) {
                const isOrientationChanged = lastOrientation !== window.orientation;
                lastOrientation = window.orientation;

                // 기본적인 리플로우
                chartInstance.reflow();

                // 화면 회전이 발생한 경우 추가 처리
                if (isOrientationChanged) {
                    const isLandscape = window.orientation === 90 || window.orientation === -90;

                    // 차트 설정 업데이트
                    chartInstance.update({
                        chart: {
                            spacing: isLandscape ? [5, 5, 10, 5] : [10, 10, 15, 10],
                            height: isLandscape ? '85vh' : '80vh'
                        }
                    }, false);

                    // y축 비율 재조정
                    chartInstance.yAxis[0].update({
                        height: isLandscape ? '70%' : '65%'
                    }, false);

                    chartInstance.redraw();
                }
            }
        }, 250);
    });

    // orientationchange 이벤트도 resize 이벤트를 발생시키도록 처리
    window.addEventListener('orientationchange', function() {
        window.dispatchEvent(new Event('resize'));
    });

    setDealModifications();
    additionalBuyingSellHistory();
    showDealStatus();
    drawCandleStickChart();
    addUnitsToValues();

    sessionStorage.clear();
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
            if (data.error){
                alert(data.errorMessage);
                return;
            }
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

            // 차트 다시 그리기 전에 현재 하이킨아시 상태 저장
            const currentHeikinAshiState = isHeikinAshi;

            //re-draw elements
            additionalBuyingSellHistory();
            showDealStatus();
            drawChart();

            // 이전 하이킨아시 상태가 true였다면 다시 하이킨아시로 변경
            if (currentHeikinAshiState) {
                const candleSeries = chartInstance.get('candle');
                if (candleSeries) {
                    candleSeries.update({
                        name: itemName + ' (하이킨아시)',
                        data: calculateHeikinAshi(candleStickDataList)
                    }, true);
                }
            }

            addUnitsToValues();
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

function drawCandleStickChart(){
    setDealHistories();

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

            //copy 한 뒤 첫 로딩에는 전날 매매이력이 있으면 현재일 허수 셋팅(이후 매매 하면 허수 데이터 pop한 뒤 신규데이터 push
            if(idx === dailyDealHistories.length - 1) {
                if( dailyDealHistories[idx - 1].additionalBuyingQuantity !== 0 ) {
                    additionalBuyingPrice.push([dailyDealHistories[idx].dealDateForTimestamp, NaN]);
                    additionalBuyingAmount.push([dailyDealHistories[idx].dealDateForTimestamp, NaN]);
                }
                if( dailyDealHistories[idx - 1].additionalSellingQuantity !== 0 ) {
                    additionalSellingPrice.push([dailyDealHistories[idx].dealDateForTimestamp, NaN]);
                    additionalSellingAmount.push([dailyDealHistories[idx].dealDateForTimestamp, NaN]);
                }
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

    // 현재 선택된 그룹화 단위를 추적하기 위한 전역 변수
    if (typeof window.currentGroupingUnit === 'undefined') {
        window.currentGroupingUnit = 'day'; // 초기값은 일봉
    }


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
                fontSize: window.innerWidth < 500 ? '18px' : '28px',
                fontWeight: 'bold'
            }
        },
        subtitle: {
            text: '시뮬레이션 기간: ' + formattedStartDate + ' - ' + formattedEndDate,
            align: 'left',
            style: {
                fontSize: window.innerWidth < 500 ? '12px' : '16px'
            }
        },
        chart: {
            zoomType: 'x',
            events: {
                load: function() {
                    this.showLoading = function() {};
                    this.hideLoading = function() {};
                }
            },
            animation: false,  // 모바일에서 성능 향상을 위해 애니메이션 비활성화
            spacing: [10, 10, 15, 10], // 차트 여백 조정 [top, right, bottom, left]
            height: null  // 자동 높이 조정 활성화
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
                type: 'month',
                count: 3,
                text: '일봉',
                events: {
                    click: function() {
                        window.currentGroupingUnit = 'day';
                        updateDataGrouping('day');
                    }
                }
            }, {
                type: 'month',
                count: 6,
                text: '주봉',
                events: {
                    click: function() {
                        window.currentGroupingUnit = 'week';
                        updateDataGrouping('week');
                    }
                }
            }, {
                type: 'year',
                count: 1,
                text: '월봉',
                events: {
                    click: function() {
                        window.currentGroupingUnit = 'month';
                        updateDataGrouping('month');
                    }
                }
            }, {
                type: 'all',
                text: '전체',
                dataGrouping: {
                    forced: true,
                    units: [['month', [1]]]
                }
            }],
            selected: 0, // 기본적으로 일봉(3개월) 선택
            inputEnabled: false
        },
        navigator: {
            enabled: true,
            height: 50,
            series: {
                type: 'line',
                color: Highcharts.getOptions().colors[0],
                dataGrouping: {
                    forced: true,
                    units: [[window.currentGroupingUnit, [1]]]
                }
            },
            xAxis: {
                events: {
                    afterSetExtremes: function(e) {
                        // Navigator의 범위가 변경될 때 버튼 상태만 업데이트
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
                },
                formatter: function() {
                    return Highcharts.numberFormat(this.value, 0, '', ',') + '%';
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
                    forced: false, // 강제 그룹화 비활성화
                    units: [
                        ['day', [1]],
                        ['week', [1]],
                        ['month', [1]]
                    ]
                }
            }
        },
        tooltip: {
            split: false,  // 모바일에서는 분할 툴팁 사용하지 않음
            followTouchMove: true,
            hideDelay: 50,
            distance: 50,
            formatter: function() {
                // 모바일 여부 확인
                const isMobile = window.innerWidth <= 500;

                // UTC+9 (한국 시간)으로 조정
                const date = new Date(this.x + 9 * 3600 * 1000);
                let tooltipText = '<b>' + Highcharts.dateFormat('%Y년 %m월 %d일', date) + '</b><br/>';

                if (isMobile) {
                    // 모바일용 간소화된 툴팁
                    if (this.points || this.point) {
                        const points = this.points || [this.point];
                        points.forEach(function(point) {
                            const series = point.series;
                            if (series.name === itemName) {
                                const close = Highcharts.numberFormat(point.close || point.y, 0, '', ',');
                                let changeRate = 0;
                                if (point.close) {
                                    const prevClose = point.prev ? point.prev.close : point.open;
                                    changeRate = ((point.close - prevClose) / prevClose * 100).toFixed(2);
                                }
                                const color = changeRate >= 0 ? 'red' : 'blue';

                                tooltipText += '<span style="color: ' + series.color + '">●</span> 종가: ' + close + '<br/>';
                                if (point.close) {
                                    tooltipText += '<span style="color: ' + color + '">등락률: ' + changeRate + '%</span><br/>';
                                }
                            } else if (series.name === '거래량') {
                                tooltipText += '<span style="color: ' + series.color + '">●</span> ' +
                                    series.name + ': ' + Highcharts.numberFormat(point.y, 0, '', ',') + '<br/>';
                            } else if (series.name === '비중') {
                                tooltipText += '<span style="color: ' + series.color + '">●</span> ' +
                                    series.name + ': ' + Highcharts.numberFormat(point.y, 0, '', ',') + '%<br/>';
                            }
                        });
                    }
                } else {
                    // 데스크톱용 기존 툴팁
                    if (this.points || this.point) {
                        const points = this.points || [this.point];
                        points.forEach(function(point) {
                            const series = point.series;
                            if (series.name === itemName) {
                                const open = Highcharts.numberFormat(point.open || point.y, 0, '', ',');
                                const high = Highcharts.numberFormat(point.high || point.y, 0, '', ',');
                                const low = Highcharts.numberFormat(point.low || point.y, 0, '', ',');
                                const close = Highcharts.numberFormat(point.close || point.y, 0, '', ',');

                                tooltipText += '<br/><span style="color: ' + series.color + '">●</span> ' + series.name + ':<br/>' +
                                    '시가: ' + open + '<br/>' +
                                    '고가: ' + high + '<br/>' +
                                    '저가: ' + low + '<br/>' +
                                    '종가: ' + close + '<br/>';

                                if (point.close) {
                                    const prevClose = point.prev ? point.prev.close : point.open;
                                    const changeRate = ((point.close - prevClose) / prevClose * 100).toFixed(2);
                                    const changeRateColor = changeRate >= 0 ? 'red' : 'blue';
                                    tooltipText += '변화율: <span style="color: ' + changeRateColor + '">' + changeRate + '%</span><br/>';
                                }
                            } else if (series.name.includes('이동평균')) {
                                tooltipText += '<br/><span style="color: ' + series.color + '">●</span> ' + series.name + ': ' +
                                    Highcharts.numberFormat(point.y, 0, '', ',') + '<br/>';
                            } else if (series.name === '거래량' || series.name === '비중') {
                                tooltipText += '<br/><span style="color: ' + series.color + '">●</span> ' + series.name + ': ' +
                                    Highcharts.numberFormat(point.y, 0, '', ',') + (series.name === '비중' ? '%' : '') + '<br/>';
                            } else {
                                tooltipText += '<br/><span style="color: ' + series.color + '">●</span> ' + series.name + ': ' +
                                    Highcharts.numberFormat(point.y, 2, '.', ',') + '<br/>';
                            }
                        });
                    }
                }

                return tooltipText;
            },
            style: {
                fontSize: window.innerWidth <= 500 ? '10px' : '14px'
            },
            padding: 5
        },
        series: [{
            id: 'candle',
            name: itemName + (isHeikinAshi ? ' (하이킨아시)' : ''),
            type: 'candlestick',
            data: isHeikinAshi ? calculateHeikinAshi(candleStickDataList) : candleStickDataList,
            dataGrouping: {
                forced: false,
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
                forced: false,
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
                forced: false,
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
                forced: false,
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
                forced: false,
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
                forced: false,
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
                forced: false,
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
                forced: false,
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
                forced: false,
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
                forced: false,
                units: [
                    ['day', [1]],
                    ['week', [1]],
                    ['month', [1]]
                ]
            },
            dataLabels: {
                enabled: true,
                formatter: function() {
                    return Highcharts.numberFormat(this.y, 0) + '%';
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
                forced: false,
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
                forced: false,
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
                    maxWidth: 500 // 모바일 화면 기준
                },
                chartOptions: {
                    rangeSelector: {
                        inputEnabled: false,
                        buttonPosition: {
                            align: 'center'  // 버튼을 중앙 정렬
                        }
                    },
                    scrollbar: {
                        height: 4  // 스크롤바 높이 줄이기
                    },
                    navigator: {
                        height: 30  // navigator 높이 줄이기
                    },
                    yAxis: [{
                        height: '65%'  // 메인 차트 영역 비율 조정
                    }, {
                        top: '70%',
                        height: '8%'
                    }, {
                        top: '80%',
                        height: '8%'
                    }, {
                        top: '90%',
                        height: '8%'
                    }],
                    legend: {
                        enabled: true,
                        layout: 'horizontal',
                        align: 'center',
                        verticalAlign: 'bottom',
                        itemStyle: {
                            fontSize: '8px'
                        },
                        itemDistance: 10,
                        x: 0,
                        y: 0
                    }
                }
            }, {
                condition: {
                    minWidth: 501  // 태블릿/데스크톱 화면
                },
                chartOptions: {
                    legend: {
                        align: 'center',
                        verticalAlign: 'bottom',
                        layout: 'horizontal',
                        itemStyle: {
                            fontSize: '14px'
                        }
                    }
                }
            }]
        },
    });

    // 3개월 범위로 초기 설정
    const extremes = chartInstance.xAxis[0].getExtremes();
    const dataMax = extremes.dataMax;
    const THREE_MONTHS = 90 * 24 * 60 * 60 * 1000;
    const newMin = Math.max(dataMax - THREE_MONTHS, extremes.dataMin);

    chartInstance.xAxis[0].setExtremes(newMin, dataMax);
    updateButtonState(newMin, dataMax);
}

// 현재의 그룹화 설정을 유지하는 함수
function maintainCurrentGrouping() {
    if (!chartInstance) return;

    const unit = window.currentGroupingUnit || 'day';

    chartInstance.series.forEach(function(series) {
        if (series.options.dataGrouping) {
            const update = {
                dataGrouping: {
                    forced: true,
                    enabled: true,
                    units: [[unit, [1]]]
                }
            };

            // Navigator 시리즈는 건너뛰기
            if (series.options.showInNavigator) return;

            series.update(update, false);
        }
    });

    // Navigator 시리즈 업데이트
    if (chartInstance.navigator && chartInstance.navigator.series) {
        chartInstance.navigator.series.forEach(function(series) {
            series.update({
                dataGrouping: {
                    forced: true,
                    enabled: true,
                    units: [[unit, [1]]]
                }
            }, false);
        });
    }

    chartInstance.redraw();
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
    if (!chartInstance) return;

    window.currentGroupingUnit = unit;
    const extremes = chartInstance.xAxis[0].getExtremes();
    const dataMax = extremes.dataMax;
    const dataMin = extremes.dataMin;

    // 각 버튼에 따른 범위 설정
    let newMin = dataMin;
    if (unit === 'day') {
        const THREE_MONTHS = 90 * 24 * 60 * 60 * 1000;
        newMin = Math.max(dataMax - THREE_MONTHS, dataMin);
    } else if (unit === 'week') {
        const SIX_MONTHS = 180 * 24 * 60 * 60 * 1000;
        newMin = Math.max(dataMax - SIX_MONTHS, dataMin);
    } else if (unit === 'month') {
        const ONE_YEAR = 365 * 24 * 60 * 60 * 1000;
        newMin = Math.max(dataMax - ONE_YEAR, dataMin);
    }

    maintainCurrentGrouping();
    chartInstance.xAxis[0].setExtremes(newMin, dataMax);
}

// 버튼 상태 업데이트 함수
function updateButtonState(min, max) {
    if (!chartInstance || !chartInstance.rangeSelector || !chartInstance.rangeSelector.buttons) {
        return;
    }

    const buttons = chartInstance.rangeSelector.buttons;
    const range = max - min;
    const THREE_MONTHS = 90 * 24 * 60 * 60 * 1000;
    const SIX_MONTHS = 180 * 24 * 60 * 60 * 1000;
    const ONE_YEAR = 365 * 24 * 60 * 60 * 1000;

    // 범위에 따라 버튼 상태만 업데이트
    buttons.forEach((button, index) => {
        if (!button || typeof button.setState !== 'function') return;

        if (index === 0 && range <= THREE_MONTHS) {
            button.setState(2);
        } else if (index === 1 && range > THREE_MONTHS && range <= SIX_MONTHS) {
            button.setState(2);
        } else if (index === 2 && range > SIX_MONTHS && range <= ONE_YEAR) {
            button.setState(2);
        } else if (index === 3 && range > ONE_YEAR) {
            button.setState(2);
        } else {
            button.setState(0);
        }
    });
}

function addUnitsToValues() {
    const statusValues = document.querySelectorAll('.status-value[data-unit]');

    statusValues.forEach(function(valueElement) {
        const unit = valueElement.dataset.unit;
        if (unit) {
            let value = valueElement.textContent.trim();
            // value가 없거나 빈 문자열이면 '0'으로 설정
            if (!value || value === '') {
                value = '0';
            }

            // 중요한 값들 bold 처리
            if (valueElement.classList.contains('important-value')) {
                valueElement.classList.add('bold');
            }

            // 수익/손실 색상 처리
            if (valueElement.classList.contains('profit-loss')) {
                // 기존 profit/loss 클래스 모두 제거
                valueElement.classList.remove('profit', 'loss');

                const numValue = parseFloat(value.replace(/,/g, ''));
                if (numValue > 0) {
                    valueElement.classList.add('profit');
                    value = '+' + value;  // 양수인 경우 + 기호 추가
                } else if (numValue < 0) {
                    valueElement.classList.add('loss');
                }
            }

            // 이미 단위가 붙어 있지 않은 경우에만 단위를 추가
            if (!value.endsWith(unit)) {
                valueElement.textContent = value + " " + unit;
            }
        }
    });
}