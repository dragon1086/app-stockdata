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
    <meta name="description" content="자동 시뮬레이션">
    <meta name="author" content="펭수르">
    <meta name="generator" content="stock-buildup 0.0.1">
    <title>자동 시뮬레이션</title>
    <!--  부트스트랩 js 사용 -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js@2.9.4/dist/Chart.min.js" integrity="sha384-zNy6FEbO50N+Cg5wap8IKA4M/ZnLJgzc6w2NqACZaK0u0FXfOWRRJOnQtpZun8ha" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/feather-icons@4.28.0/dist/feather.min.js"></script>

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

        .btn-manual,
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

        .btn-home:hover,
        .btn-manual:hover,
        .btn-outline-light:hover {
            background-color: #ffffff !important;
            color: #3498db !important;
        }

        .btn-home i,
        .btn-manual i {
            margin-right: 0.5rem;
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
    </style>
    <script>
        //setting values
        var myAverageUnitPriceList = [];
        var volumeList = [];
        var candleStickDataList = [];
        var additionalBuyingPrice = [];
        var additionalSellingPrice = [];
        var additionalBuyingAmount = [];
        var additionalSellingAmount = [];
        var groupingUnits = [['day', [1]], ['week', [1]], ['month', [1, 2, 3, 4, 6]]];
        var startDate = "${startDate}";
        var endDate = "${endDate}";
        var itemName = "${itemName}";

        <c:if test="${isError == 'false'}">
        <c:forEach items="${dailyDealHistories}" var="dailyDealHistory">
        candleStickDataList.push([${dailyDealHistory.dealDateForTimestamp}, ${dailyDealHistory.startPrice}, ${dailyDealHistory.highPrice}, ${dailyDealHistory.lowPrice}, ${dailyDealHistory.closingPrice}]);
        volumeList.push([${dailyDealHistory.dealDateForTimestamp}, ${dailyDealHistory.tradeVolume}]);
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


        $(function(){
            additionalBuyingSellHistory();
            document.cookie = "SameSite=None; Secure";
            $(document).ready(function() {
                drawCandleStickChart();
                initTooltips();
            });
        });

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

        function additionalBuyingSellHistory(){
            <c:forEach items="${dealModifications}" var="dealModification" varStatus="status">
                var innerDivTagForDate = $('<div/>', {class: 'input-group mb-3'});
                var innerDivTagForSelling = $('<div/>', {class: 'input-group mb-3'});
                var innerDivTagForBuying = $('<div/>', {class: 'input-group mb-3'});
                var spanModifyDate = $('<span/>', {class: 'input-group-text'}).text("수정 날짜");
                var inputModifyDate = $('<input/>', {
                    readOnly: 'true',
                    type: 'date',
                    class: 'form-control',
                    name: 'modifyDate',
                    value: "<javatime:parseLocalDate value='${dealModification.modifyDate}' pattern='yyyy-MM-dd' />"
                });
                var spanSellPercentHistory = $('<span/>', {class: 'input-group-text'}).text("매도 비중%");
                var inputSellPercentHistory = $('<input/>', {
                    readOnly: 'true',
                    type: 'text',
                    class: 'form-control',
                    name: 'sellPercent',
                    value: ${dealModification.sellPercent}
                });
                var spanSellPriceHistory = $('<span/>', {class: 'input-group-text'}).text("매도 가격");
                var inputSellPriceHistory = $('<input/>', {
                    readOnly: 'true',
                    type: 'text',
                    class: 'form-control',
                    name: 'sellPrice',
                    value: ${dealModification.sellPrice}
                });
                var spanBuyPercentHistory = $('<span/>', {class: 'input-group-text'}).text("매수 비중%");
                var inputBuyPercentHistory = $('<input/>', {
                    readOnly: 'true',
                    type: 'text',
                    class: 'form-control',
                    name: 'buyPercent',
                    value: ${dealModification.buyPercent}
                });
                var spanBuyPriceHistory = $('<span/>', {class: 'input-group-text'}).text("매수 가격");
                var inputBuyPriceHistory = $('<input/>', {
                    readOnly: 'true',
                    type: 'text',
                    class: 'form-control',
                    name: 'buyPrice',
                    value: ${dealModification.buyPrice}
                });

                innerDivTagForDate.append(spanModifyDate, inputModifyDate);
                innerDivTagForSelling.append(spanSellPercentHistory, inputSellPercentHistory, spanSellPriceHistory, inputSellPriceHistory);
                innerDivTagForBuying.append(spanBuyPercentHistory, inputBuyPercentHistory, spanBuyPriceHistory, inputBuyPriceHistory);

                $("#additionalBuyingSellHistory").append(innerDivTagForDate, innerDivTagForSelling, innerDivTagForBuying);
            </c:forEach>
        }

        function drawCandleStickChart(){
            const container = document.getElementById('container');
            if (!container) {
                console.error('차트를 그릴 컨테이너를 찾을 수 없습니다.');
                return;
            }


            // 이동평균 계산
            const fiveMA = calculateMovingAverage(candleStickDataList, 5);
            const twentyMA = calculateMovingAverage(candleStickDataList, 20);
            const sixtyMA = calculateMovingAverage(candleStickDataList, 60);
            const oneTwentyMA = calculateMovingAverage(candleStickDataList, 120);

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
            const chart = Highcharts.stockChart('container', {
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
                        click: function (event) {
                            const clickedDate = new Date(event.xAxis[0].value);
                            const formattedDate = clickedDate.toISOString().split('T')[0];

                            const modificationForm = document.getElementById('modifyCalculation');
                            const parentDiv = document.getElementById('parentDivForModifiyCalculation');

                            if (modificationForm && parentDiv) {
                                const newModificationForm = document.createElement('div');
                                newModificationForm.className = "input-group mb-3";
                                newModificationForm.innerHTML = `
                                    <div class='input-group mb-3'>
                                        <span class='input-group-text' id='basic-addon1'>수정 날짜</span>
                                        <input type='date' class='form-control' name='modifyDate' value='${formattedDate}' aria-label='modifyDate' aria-describedby='basic-addon1'>
                                    </div>
                                    <div class='input-group mb-3'>
                                        <span class='input-group-text' id='basic-addon2'>매도 비중%(수정시점비중)</span>
                                        <input type='text' class='form-control' name='sellPercent' placeholder='% 제외하고 입력하세요(소수점 제외)' aria-label='sellPercent' aria-describedby='basic-addon2'>
                                        <span class='input-group-text' id='basic-addon3'>매도 가격</span>
                                        <input type='text' class='form-control' name='sellPrice' placeholder='매도하실 금액을 입력하세요(저가와 고가 사이)' aria-label='sellPrice' aria-describedby='basic-addon3'>
                                    </div>
                                    <div class='input-group mb-3'>
                                        <span class='input-group-text' id='basic-addon4'>매수 비중%(수정시점비중)</span>
                                        <input type='text' class='form-control' name='buyPercent' placeholder='% 제외하고 입력하세요(소수점 제외)' aria-label='buyPercent' aria-describedby='basic-addon4'>
                                        <span class='input-group-text' id='basic-addon5'>매수 가격</span>
                                        <input type='text' class='form-control' name='buyPrice' placeholder='매수하실 금액을 입력하세요(저가와 고가 사이)' aria-label='buyPrice' aria-describedby='basic-addon5'>
                                    </div>
                                `;

                                parentDiv.appendChild(newModificationForm);
                                initTooltips();
                            }
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
                    selected: 1,
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
                    height: '65%',
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
                    top: '70%',
                    height: '15%',
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
                    onSeries: 'candle',
                    color: '#afa400',
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
                        units: groupingUnits
                    },
                    onSeries: 'candle',
                    color: '#007eff',
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
                        units: groupingUnits
                    },
                    color: '#383832',
                    lineWidth: 1,
                    onSeries: 'candle'
                }, {
                    type: 'spline',
                    name: '20일 이동평균',
                    data: twentyMA,
                    dataGrouping: {
                        units: groupingUnits
                    },
                    color: '#ff0000',
                    lineWidth: 1,
                    onSeries: 'candle'
                }, {
                    type: 'spline',
                    name: '60일 이동평균',
                    data: sixtyMA,
                    dataGrouping: {
                        units: groupingUnits
                    },
                    color: '#514fff',
                    lineWidth: 1,
                    onSeries: 'candle'
                }, {
                    type: 'spline',
                    name: '120일 이동평균',
                    data: oneTwentyMA,
                    dataGrouping: {
                        units: groupingUnits
                    },
                    color: '#ffae00',
                    lineWidth: 1,
                    onSeries: 'candle'
                }, {
                    type: 'column',
                    id: 'buyingAmount',
                    name: '매수 금액',
                    data: additionalBuyingAmount,
                    dataGrouping: {
                        units: groupingUnits
                    },
                    yAxis: 2,
                    color: '#FF0000'
                }, {
                    type: 'column',
                    name: '매도 금액',
                    data: additionalSellingAmount,
                    dataGrouping: {
                        units: groupingUnits
                    },
                    yAxis: 2,
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

        function initTooltips() {
            var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
            tooltipTriggerList.forEach(function (tooltipTriggerEl) {
                new bootstrap.Tooltip(tooltipTriggerEl)
            })
        }
    </script>
</head>
<body>
<!-- Navbar -->
<nav class="navbar navbar-expand-lg fixed-top navbar-dark">
    <div class="container">
        <a class="navbar-brand" href="/">자동 시뮬레이션</a>
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
                    <a class="btn-manual" href="/buildupManual">
                        <i class="fas fa-book"></i>
                        <span>매뉴얼</span>
                    </a>
                </li>
                <li class="nav-item">
                    <% if (session.getAttribute("sessionUser") == null) { %>
                    <button class="btn btn-outline-light" onclick="location.href='/login/google'">Google로 로그인</button>
                    <% } else { %>
                    <button class="btn btn-outline-light me-2" onclick="location.href='/logout/google'">로그아웃</button>
                    <% } %>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- 메인 컨테이너 -->
<div class="container main-container">
    <h1 class="text-center mb-4">시뮬레이션 결과
        <i class="fas fa-question-circle tooltip-icon"
           data-bs-toggle="tooltip"
           data-bs-placement="right"
           title="자동 시뮬레이션 결과를 보여줍니다. 그래프와 상세 정보를 확인하세요."></i>
    </h1>

    <c:choose>
    <c:when test="${isError == 'true'}">
    <div class="alert alert-danger" role="alert">
        <h4 class="alert-heading">오류 발생</h4>
        <p>${errorMessage}</p>
    </div>
    </c:when>
    <c:otherwise>
    <div class="row">
        <div class="col-md-6">
            <h2>수익률: <fmt:formatNumber value="${earningRate}" pattern="#,###.00" />%</h2>
            <h2>실현손익: <fmt:formatNumber value="${earningAmount}" pattern="#,###" />원</h2>
            <h2>총 투입금액: <fmt:formatNumber value="${sumOfPurchaseAmount}" pattern="#,###" />원</h2>
            <h2>총 매도금액: <fmt:formatNumber value="${sumOfSellingAmount}" pattern="#,###" />원</h2>
        </div>
        <div class="col-md-6">
            <h2>총 매입수량: <fmt:formatNumber value="${sumOfPurchaseQuantity}" pattern="#,###" />주</h2>
            <h2>총 매도수량: <fmt:formatNumber value="${sumOfSellingQuantity}" pattern="#,###" />주</h2>
            <h2>총 매도수수료(0.3%): <fmt:formatNumber value="${sumOfCommission}" pattern="#,###" />원</h2>
            <h2>현재 평가금액: <fmt:formatNumber value="${totalAmount}" pattern="#,###" />원</h2>
        </div>
    </div>
    <div class="row mt-4">
        <div class="col-md-6">
            <h2>전일대비 증가 횟수: <fmt:formatNumber value="${countOfDayOnDayClosingPriceIncrease}" pattern="#,###" />회</h2>
        </div>
        <div class="col-md-6">
            <h2>전일대비 감소 횟수: <fmt:formatNumber value="${countOfDayOnDayClosingPriceDecrease}" pattern="#,###" />회</h2>
        </div>
    </div>

    <hr class="my-4">

    <div id="container" style="height: 800px; min-width: 310px"></div>


    <form id="modifyCalculation" action="buildup-calculate-modify" method="post" name="calculateRequestFrom">
        <!-- 추가 매수/매도 이력을 위한 hidden 필드들 -->
        <div id="hiddenModificationFields" style="display: none;">
            <c:forEach items="${dealModifications}" var="dealModification" varStatus="status">
                <input type="hidden" name="modifyDate" value="<javatime:parseLocalDate value='${dealModification.modifyDate}' pattern='yyyy-MM-dd' />">
                <input type="hidden" name="sellPercent" value="${dealModification.sellPercent}">
                <input type="hidden" name="sellPrice" value="${dealModification.sellPrice}">
                <input type="hidden" name="buyPercent" value="${dealModification.buyPercent}">
                <input type="hidden" name="buyPrice" value="${dealModification.buyPrice}">
            </c:forEach>
        </div>

        <div class="px-4 py-5 my-5 text-center" id="parentDivForModifiyCalculation">
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon-company-name">기업 이름</span>
                <input type="text" class="form-control" id="companyName" name="companyName" value="${companyName}" readonly aria-label="companyName" aria-describedby="basic-addon-company-name">
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon-simulation-mode">시뮬레이션 모드</span>
                <select class="form-select" id="simulationMode" name="simulationMode" aria-label="simulationMode" aria-describedby="basic-addon-simulation-mode">
                    <option value="dailyClosingPrice" ${simulationMode == 'dailyClosingPrice' ? 'selected' : ''}>매일종가매수</option>
                    <option value="minusCandle" ${simulationMode == 'minusCandle' ? 'selected' : ''}>음봉일 때만 매수</option>
                </select>
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon-start-date">시작 날짜</span>
                <input type="date" class="form-control" id="startDate" name="startDate" value="${startDate}" readonly aria-label="startDate" aria-describedby="basic-addon-start-date">
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon-end-date">종료 날짜</span>
                <input type="date" class="form-control" id="endDate" name="endDate" value="${endDate}" readonly aria-label="endDate" aria-describedby="basic-addon-end-date">
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon-buildup-amount">매일 매수 금액</span>
                <input type="text" class="form-control" id="buildupAmount" name="buildupAmount" value="${buildupAmount}" readonly aria-label="buildupAmount" aria-describedby="basic-addon-buildup-amount">
            </div>
            <!-- 동적으로 생성되는 입력 필드들이 여기에 추가됩니다 -->
        </div>
        <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
            <button type="submit" class="btn btn-primary btn-lg px-4 gap-3">수정 전송</button>
        </div>
        <div class="row g-1 text-left" style="padding:20px">
            <h5>
                <i class="fas fa-info-circle tooltip-icon"
                   data-bs-toggle="tooltip"
                   data-bs-placement="right"
                   title="그래프의 배경을 클릭하시면, 수정할 수 있는 행이 추가됩니다. 1개 행에 매수/매도 다 입력해도 되고, 한쪽만 입력해도 됩니다. 단, 매수/매도 비중을 입력했으면 매수/매도 단가도 필수입니다. 비중과 단가 중 하나라도 미입력 시 계산 무시합니다. 같은날짜에 더 매수/매도 하고 싶으시면, 행을 더 추가하시면 됩니다. 날짜가 없는 행은 계산에서 제외됩니다. 매도 시 매도금액의 0.3%를 수수료차원에서 실현손익에서 뺍니다."></i>
                수정 안내
            </h5>
        </div>
    </form>

        <hr class="my-4">

        <!-- 추가 매수/매도 이력 -->
        <div class="px-4 py-5 my-5 text-left" id="additionalBuyingSellHistory">
            <h2><strong>추가 매수/매도 이력</strong></h2>
        </div>

        <hr class="my-4">

        <h2 class="mb-3">매매 이력</h2>
        <div class="table-responsive">
            <table class="table table-striped table-sm">
                <thead>
                <tr>
                    <th scope="col">거래일</th>
                    <th scope="col">종가</th>
                    <th scope="col">종가 구매수량</th>
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
                        <td><fmt:formatNumber value="${dailyDealHistory.closingPrice}" pattern="#,###" />원</td>
                        <td><fmt:formatNumber value="${dailyDealHistory.closingPurchaseQuantity}" pattern="#,###" />주</td>
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

        <hr class="my-4">

        <h2 class="mb-3">최초 입력 요청값</h2>
        <div class="row">
            <div class="col-md-6">
                <div class="mb-3">
                    <label for="inputSimulationMode" class="form-label">시뮬레이션 모드</label>
                    <select id="inputSimulationMode" class="form-select form-select-lg mb-3" name="simulationMode" aria-label="simulationMode" aria-describedby="basic-addon0"
                            onfocus="this.initialSelect=this.selectedIndex;" onchange="this.selectedIndex=this.initialSelect;">
                        <option value="dailyClosingPrice" selected>매일종가매수</option>
                        <option value="minusCandle">음봉일 때만 매수</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="inputCompanyName" class="form-label">기업 이름</label>
                    <input type="text" class="form-control" id="inputCompanyName" value="${companyName}" readonly>
                </div>
            </div>
            <div class="col-md-6">
                <div class="mb-3">
                    <label for="inputStartDate" class="form-label">시작 날짜</label>
                    <input type="date" class="form-control" id="inputStartDate" value="${startDate}" readonly>
                </div>
                <div class="mb-3">
                    <label for="inputEndDate" class="form-label">매도 날짜</label>
                    <input type="date" class="form-control" id="inputEndDate" value="${endDate}" readonly>
                </div>
                <div class="mb-3">
                    <label for="inputBuildupAmount" class="form-label">매일 매수 금액</label>
                    <input type="text" class="form-control" id="inputBuildupAmount" value="${buildupAmount}" readonly>
                </div>
            </div>
        </div>
    </c:otherwise>
    </c:choose>
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

<!-- Footer -->

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