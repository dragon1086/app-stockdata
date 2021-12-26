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
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="일봉 매매 훈련보조기">
    <meta name="author" content="펭수르">
    <meta name="generator" content="stock-buildup 0.0.1">
    <title>일봉 매매 훈련보조기</title>
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
    </style>
    <!-- Custom styles for this template -->
    <link href="/resources/css/buildup.css" rel="stylesheet">
    <script>
        $(function(){
            additionalBuyingSellHistory();
            $('input[id=thisModifyDate]').attr('value',"${nextTryDate}");
        });

        function additionalBuyingSellHistory(){
            <c:forEach items="${dealModifications}" var="dealModification">
                <c:if test="${(dealModification.buyPercent != 0 && dealModification.buyPrice !=0) || (dealModification.sellPercent != 0 && dealModification.sellPrice !=0)}">
                    var innerDivTag = $('<div/>', {class: 'input-group mb-3'});
                    var spanModifyDate = $('<span/>', {class: 'input-group-text'}).text("수정 날짜");
                    var inputModifyDate = $('<input/>', {readOnly: 'true', type: 'date', class: 'form-control', name: 'modifyDate', value: "<javatime:parseLocalDate value='${dealModification.modifyDate}' pattern='yyyy-MM-dd' />"});
                    var spanSellPercentHistory = $('<span/>', {class: 'input-group-text'}).text("매도 비중%(당시 보유비중의 몇%)");
                    var inputSellPercentHistory = $('<input/>', {readOnly: 'true', type: 'text', class: 'form-control', name: 'sellPercent', value: ${dealModification.sellPercent}});
                    var spanSellPriceHistory = $('<span/>', {class: 'input-group-text'}).text("매도 가격");
                    var inputSellPriceHistory = $('<input/>', {readOnly: 'true', type: 'text', class: 'form-control', name: 'sellPrice', value: ${dealModification.sellPrice}});
                    var spanBuyPercentHistory = $('<span/>', {class: 'input-group-text'}).text("매수 비중%(당시 보유비중의 몇%)");
                    var inputBuyPercentHistory = $('<input/>', {readOnly: 'true', type: 'text', class: 'form-control', name: 'buyPercent', value: ${dealModification.buyPercent}});
                    var spanBuyPriceHistory = $('<span/>', {class: 'input-group-text'}).text("매수 가격");
                    var inputBuyPriceHistory = $('<input/>', {readOnly: 'true', type: 'text', class: 'form-control', name: 'buyPrice', value: ${dealModification.buyPrice}});

                    var divTag = document.createElement('div');
                    divTag.className = "input-group mb-3";

                    innerDivTag.append(spanModifyDate, inputModifyDate, spanSellPercentHistory, inputSellPercentHistory
                        , spanSellPriceHistory, inputSellPriceHistory, spanBuyPercentHistory, inputBuyPercentHistory, spanBuyPriceHistory, inputBuyPriceHistory);
                    divTag.append(innerDivTag.get(0));

                    document.getElementById("additionalBuyingSellHistory").appendChild(divTag);
                </c:if>
            </c:forEach>

        }
    </script>
</head>
<body>
<div class="container">
    <div class="container-fluid">
        <button type="button" class="btn btn-dark" onclick="location.href='/'">초기화면 돌아가기</button>
        <div class="row">
            <main class="col-md-12 ms-sm-auto col-lg-12 px-md-4">
                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3">
                    <div>
                        <c:choose>
                            <c:when test="${isError == 'true'}">
                                <h1>${errorMessage}</h1>
                            </c:when>
                            <c:otherwise>
                                <h1>매매 현황</h1>
                                <p></p>
                                <h2>실현수익률 : <fmt:formatNumber value="${earningRate}" pattern="#,###.00" />%</h2>
                                <h2>실현손익 : <fmt:formatNumber value="${earningAmount}" pattern="#,###" />원</h2>
                                <h2>슬랏 할당금액 : <fmt:formatNumber value="${slotAmount}" pattern="#,###" />원</h2>
                                <h2>현재 비중 : <fmt:formatNumber value="${portion}" pattern="#,###.00" />% (전량매도 후 남으면 그만큼 손실이고, 마이너스면 이익이라는 의미입니다)</h2>
                                <h2>슬랏 예수금 : <fmt:formatNumber value="${remainingSlotAmount}" pattern="#,###" />원</h2>
                                <h2>슬랏 예수금 비중 : <fmt:formatNumber value="${remainingPortion}" pattern="#,###.00" />% (전량매도 후 최초 할당금액보다 적으면 그만큼 손실이고, 많으면 이익이라는 의미입니다)</h2>
                                <h2>총 매입금액 : <fmt:formatNumber value="${sumOfPurchaseAmount}" pattern="#,###" />원</h2>
                                <h2>총 매도금액 : <fmt:formatNumber value="${sumOfSellingAmount}" pattern="#,###" />원</h2>
                                <h2>총 매입수량 : <fmt:formatNumber value="${sumOfPurchaseQuantity}" pattern="#,###" />주</h2>
                                <h2>총 매도수량 : <fmt:formatNumber value="${sumOfSellingQuantity}" pattern="#,###" />주</h2>
                                <h2>총 매도수수료(0.3%) : <fmt:formatNumber value="${sumOfCommission}" pattern="#,###" />원</h2>
                                <h2>현재 평가금액 : <fmt:formatNumber value="${totalAmount}" pattern="#,###" />원</h2>
                                <h2>현재 평가손익 : <fmt:formatNumber value="${valuationPercent}" pattern="#,###.00" />%</h2>
                                <h2>현재 평균단가 : <fmt:formatNumber value="${averageUnitPrice}" pattern="#,###" />원</h2>
                                <h2>현재 종가 : <fmt:formatNumber value="${currentClosingPrice}" pattern="#,###" />원</h2>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <hr style="height:3px;color:#dc874f">
                <form id="modifyCalculation" action="deal-calculate-modify" method="post" name="calculateRequestFrom">
                    <div class="px-4 py-5 my-5 text-center" id="parentDivForModifiyCalculation">
                        <h1>아래 그래프를 보시고, 매수/매도/기다림을 선택하세요. </h1>
                        <h1>매수/매도 하고 싶으시면 단가와 비중 입력 후 "다음", 그냥 넘기고 싶으시면 "다음"버튼 눌러주세요. </h1>
                        <h3>1개 행에 매수/매도 다 입력해도 되고, 한쪽만 입력해도 됩니다.</h3>
                        <h3>단, 매수/매도 비중을 입력했으면 매수/매도 단가도 필수입니다. 비중과 단가 중 하나라도 미입력 시 계산 무시합니다.</h3>
                        <h3>날짜가 없으면 계산에서 제외됩니다.</h3>
                        <h3>매도 시 매도금액의 0.3%를 수수료차원에서 실현손익에서 뺍니다.</h3>
                        <div></div>
                        <div class="input-group mb-3" id= "modifyInputGroup">
                            <span class="input-group-text" id="basic-addon1">수정 날짜</span>
                            <input readonly="true" id="thisModifyDate" type="date" class="form-control" name="modifyDate" aria-label="modifyDate" aria-describedby="basic-addon1">
                            <span class="input-group-text" id="basic-addon2">매도 비중%(현시점 보유비중의 몇%)</span>
                            <input type="text" class="form-control" name="sellPercent" placeholder="% 제외하고 입력하세요(소수점 제외)" aria-label="sellPercent" aria-describedby="basic-addon2">
                            <span class="input-group-text" id="basic-addon3">매도 가격</span>
                            <input type="text" class="form-control" name="sellPrice" placeholder="매도하실 금액을 입력하세요(저가와 고가 사이)" aria-label="sellPrice" aria-describedby="basic-addon3">
                            <span class="input-group-text" id="basic-addon4">매수 비중%(현시점 보유비중의 몇%)</span>
                            <input type="text" class="form-control" name="buyPercent" placeholder="% 제외하고 입력하세요(소수점 제외)" aria-label="buyPercent" aria-describedby="basic-addon4">
                            <span class="input-group-text" id="basic-addon5">매수 가격</span>
                            <input type="text" class="form-control" name="buyPrice" placeholder="매수하실 금액을 입력하세요(저가와 고가 사이)" aria-label="buyPrice" aria-describedby="basic-addon5">
                        </div>
                    </div>
                    <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
                        <button type="submit" class="btn btn-primary btn-lg px-4 gap-3" onclick="return">다음</button>
                    </div>
                    <div></div>
                    <div class="px-4 py-5 my-5 text-center">
                        <h1>일봉차트 관찰 훈련도 해보세요.</h1>
                        <h3>훈련을 위해 다음날 예상되는 시가/종가/저가/고가를 입력해보세요. 계산에는 영향을 안미칩니다.</h3>
                        <div></div>
                        <div class="input-group mb-3">
                            <span class="input-group-text" id="training1">시가</span>
                            <input type="text" class="form-control" name="startPrice" placeholder="예상되는 시가 입력해보세요" aria-label="startPrice" aria-describedby="startPrice">
                            <span class="input-group-text" id="training2">종가</span>
                            <input type="text" class="form-control" name="closingPrice" placeholder="예상되는 종가 입력해보세요" aria-label="closingPrice" aria-describedby="closingPrice">
                            <span class="input-group-text" id="training3">저가</span>
                            <input type="text" class="form-control" name="lowPrice" placeholder="예상되는 저가 입력해보세요" aria-label="lowPrice" aria-describedby="lowPrice">
                            <span class="input-group-text" id="training4">고가</span>
                            <input type="text" class="form-control" name="highPrice" placeholder="예상되는 고가 입력해보세요" aria-label="highPrice" aria-describedby="highPrice">
                        </div>
                    </div>

                    <hr style="height:3px;color:#dc874f">

                    <div id="container" style="height: 800px; min-width: 310px"></div>
                    <script>
                        function drawCandleStickChart(){
                            //setting values
                            var candleStickDataList = [];
                            var volumeList = [];
                            var portionList = [];
                            var myAverageUnitPriceList = [];
                            var additionalBuyingPrice = [];
                            var additionalSellingPrice = [];
                            var additionalBuyingAmount = [];
                            var additionalSellingAmount = [];
                            var groupingUnits = [['day', [1]], ['week', [1]], ['month', [1, 2, 3, 4, 6]]];

                            <c:if test="${isError == 'false'}">
                                <c:forEach items="${dailyDealHistories}" var="dailyDealHistory">
                                    candleStickDataList.push([${dailyDealHistory.dealDateForTimestamp}, ${dailyDealHistory.startPrice}, ${dailyDealHistory.highPrice}, ${dailyDealHistory.lowPrice}, ${dailyDealHistory.closingPrice}]);
                                    volumeList.push([${dailyDealHistory.dealDateForTimestamp}, ${dailyDealHistory.tradeVolume}]);
                                    portionList.push([${dailyDealHistory.dealDateForTimestamp}, ${dailyDealHistory.portion}])
                                    <c:if test="${dailyDealHistory.myAverageUnitPrice != 0}">
                                        myAverageUnitPriceList.push([${dailyDealHistory.dealDateForTimestamp},${dailyDealHistory.myAverageUnitPrice}]);
                                    </c:if>
                                    <c:if test="${dailyDealHistory.additionalBuyingQuantity != 0}">
                                        additionalBuyingPrice.push([${dailyDealHistory.dealDateForTimestamp},${dailyDealHistory.additionalBuyingAmount / dailyDealHistory.additionalBuyingQuantity}]);
                                        additionalBuyingAmount.push([${dailyDealHistory.dealDateForTimestamp},${dailyDealHistory.additionalBuyingAmount}]);
                                    </c:if>
                                    <c:if test="${dailyDealHistory.additionalSellingQuantity != 0}">
                                        additionalSellingPrice.push([${dailyDealHistory.dealDateForTimestamp},${dailyDealHistory.additionalSellingAmount / dailyDealHistory.additionalSellingQuantity}]);
                                        additionalSellingAmount.push([${dailyDealHistory.dealDateForTimestamp},${dailyDealHistory.additionalSellingAmount}]);
                                    </c:if>
                                </c:forEach>
                            </c:if>

                            document.addEventListener('DOMContentLoaded', function () {
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
                                        text: '일봉차트와 평균단가 그래프(${itemName})',
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
                                        zoomType: 'xy'
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
                                            text: '${itemName}'
                                        },
                                        height: '50%',
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
                                            text: '거래량'
                                        },
                                        top: '55%',
                                        height: '25%',
                                        offset: 0,
                                        lineWidth: 2
                                    }, {
                                        labels: {
                                            align: 'right',
                                            x: -3
                                        },
                                        title: {
                                            text: '비중'
                                        },
                                        top: '85%',
                                        height: '15%',
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
                                        name: '${itemName}',
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
                                            enabled: true,
                                            radius: 10
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
                                            enabled: true,
                                            radius: 10
                                        }
                                    }, {
                                        type: 'line',
                                        id: 'portion',
                                        name: '비중',
                                        data: portionList,
                                        dataGrouping: {
                                            units: groupingUnits
                                        },
                                        yAxis: 2,
                                        color: '#000000',
                                        onSeries: 'candle'
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
                            });
                        }
                        drawCandleStickChart();
                    </script>

                    <div class="px-4 py-5 my-5 text-center">
                        <h3>최초 입력 요청값</h3>
                        <div class="input-group mb-3">
                            <span class="input-group-text" id="input-addon1">기업 이름</span>
                            <input readonly="true" type="text" id="inputCompanyName" class="form-control" name="companyName" aria-label="companyName" aria-describedby="input-addon1" value="">
                            <span class="input-group-text" id="input-addon2">시작 날짜</span>
                            <input readonly="true" type="date" id="inputStartDate" class="form-control" name="startDate" aria-label="startDate" aria-describedby="input-addon2" value="">
                            <span class="input-group-text" id="input-addon3">최근 날짜</span>
                            <input readonly="true" type="date" id="inputEndDate" class="form-control" name="endDate" aria-label="endDate" aria-describedby="input-addon3" value="">
                            <span class="input-group-text" id="input-addon4">설정 금액</span>
                            <input readonly="true" type="text" id="inputSlotAmount" class="form-control" name="slotAmount" aria-label="slotAmount" aria-describedby="input-addon4" value="">
                            <span class="input-group-text" id="input-addon5">시작 비중</span>
                            <input readonly="true" type="text" id="inputPortion" class="form-control" name="portion" aria-label="portion" aria-describedby="input-addon5" value="">

                            <script>
                                $('input[id=inputCompanyName]').attr('value',"${companyName}");
                                $('input[id=inputStartDate]').attr('value',"${startDate}");
                                $('input[id=inputEndDate]').attr('value',"${endDate}");
                                $('input[id=inputSlotAmount]').attr('value',"${slotAmount}");
                                $('input[id=inputPortion]').attr('value',<fmt:formatNumber value="${portion}" pattern="#,###" />);
                            </script>
                        </div>
                    </div>

                    <hr style="height:3px;color:#dc874f">
                    <div class="px-4 py-5 my-5 text-center" id="additionalBuyingSellHistory">
                        <h2>추가 매수/매도 이력</h2>
                    </div>
                </form>

                <hr style="height:3px;color:#dc874f">
                <h2>날짜별 전체 이력</h2>
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
                        <tbody>
                        <c:forEach items="${dailyDealHistoriesDesc}" var="dailyDealHistory">
                            <tr>
                                <td>${dailyDealHistory.dealDate}</td>
                                <td><fmt:formatNumber value="${dailyDealHistory.buyPrice}" pattern="#,###" />원</td>
                                <td><fmt:formatNumber value="${dailyDealHistory.buyPercent}" pattern="#,###" />%</td>
                                <td><fmt:formatNumber value="${dailyDealHistory.sellPrice}" pattern="#,###" />원</td>
                                <td><fmt:formatNumber value="${dailyDealHistory.sellPercent}" pattern="#,###" />%</td>
                                <td><fmt:formatNumber value="${dailyDealHistory.myAverageUnitPrice}" pattern="#,###" />원</td>
                                <td><fmt:formatNumber value="${dailyDealHistory.additionalBuyingQuantity}" pattern="#,###" />주</td>
                                <td><fmt:formatNumber value="${dailyDealHistory.additionalBuyingAmount}" pattern="#,###" />원</td>
                                <td><fmt:formatNumber value="${dailyDealHistory.additionalSellingQuantity}" pattern="#,###" />주</td>
                                <td><fmt:formatNumber value="${dailyDealHistory.additionalSellingAmount}" pattern="#,###" />원</td>
                                <td><fmt:formatNumber value="${dailyDealHistory.commission}" pattern="#,###" />원</td>
                                <td><fmt:formatNumber value="${dailyDealHistory.realizedEarningAmount}" pattern="#,###" />원</td>
                                <td><fmt:formatNumber value="${dailyDealHistory.remainingAmount}" pattern="#,###" />원</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </main>
        </div>
    </div>
</div>

<script type="text/javascript" src="/resources/js/bootstrap.js?ver=123"></script>
<script src="https://cdn.jsdelivr.net/npm/feather-icons@4.28.0/dist/feather.min.js" integrity="sha384-uO3SXW5IuS1ZpFPKugNNWqTZRRglnUJK6UAZ/gxOX80nxEkN9NcGZTftn6RzhGWE" crossorigin="anonymous"></script>
<script src="https://code.highcharts.com/stock/highstock.js"></script>
<script src="https://code.highcharts.com/stock/modules/exporting.js"></script>

</body>
</html>