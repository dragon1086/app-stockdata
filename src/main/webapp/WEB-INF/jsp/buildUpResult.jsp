<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
    <meta name="description" content="빌드업 시뮬레이션기">
    <meta name="author" content="펭수르">
    <meta name="generator" content="stock-buildup 0.0.1">
    <title>빌드업 시뮬레이션기</title>
    <!--  부트스트랩 js 사용 -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js@2.9.4/dist/Chart.min.js" integrity="sha384-zNy6FEbO50N+Cg5wap8IKA4M/ZnLJgzc6w2NqACZaK0u0FXfOWRRJOnQtpZun8ha" crossorigin="anonymous"></script>

    <link rel="stylesheet" href="/resources/css/bootstrap.css">
    <link rel="stylesheet" href="/resources/css/dashboard.css">
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
        $(function(){
            document.cookie = "SameSite=None; Secure";
        });
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
                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3">
                    <div>
                        <c:choose>
                            <c:when test="${isError == 'true'}">
                                <h1>${errorMessage}</h1>
                            </c:when>
                            <c:otherwise>
                                <h1><strong>시뮬레이션 결과</strong></h1>
                                <p></p>
                                <h2>수익률 : <fmt:formatNumber value="${earningRate}" pattern="#,###.00" />%</h2>
                                <h2>실현손익 : <fmt:formatNumber value="${earningAmount}" pattern="#,###" />원</h2>
                                <h2>총 투입금액 : <fmt:formatNumber value="${sumOfPurchaseAmount}" pattern="#,###" />원</h2>
                                <h2>총 매도금액 : <fmt:formatNumber value="${sumOfSellingAmount}" pattern="#,###" />원</h2>
                                <h2>총 매입수량 : <fmt:formatNumber value="${sumOfPurchaseQuantity}" pattern="#,###" />주</h2>
                                <h2>총 매도수량 : <fmt:formatNumber value="${sumOfSellingQuantity}" pattern="#,###" />주</h2>
                                <h2>총 매도수수료(0.3%) : <fmt:formatNumber value="${sumOfCommission}" pattern="#,###" />원</h2>
                                <h2>현재 평가금액 : <fmt:formatNumber value="${totalAmount}" pattern="#,###" />원</h2>
                                <h2>전일대비 증가 횟수 : <fmt:formatNumber value="${countOfDayOnDayClosingPriceIncrease}" pattern="#,###" />회</h2>
                                <h2>전일대비 감소 횟수 : <fmt:formatNumber value="${countOfDayOnDayClosingPriceDecrease}" pattern="#,###" />회</h2>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <hr style="height:3px;color:#dc874f">
                <canvas class="my-4 w-100" id="myChart" width="900" height="700"></canvas>

                <hr style="height:3px;color:#dc874f">
                <div id="container" style="height: 800px; min-width: 310px"></div>
                <script>
                    function drawCandleStickChart(){
                        //setting values
                        var candleStickDataList = [];
                        var volumeList = [];
                        var myAverageUnitPriceList = [];
                        var fiveMovingAverageList = [];
                        var twentyMovingAverageList = [];
                        var sixtyMovingAverageList = [];
                        var oneTwentyMovingAverageList = [];
                        var groupingUnits = [['day', [1]], ['week', [1]], ['month', [1, 2, 3, 4, 6]]];

                        <c:if test="${isError == 'false'}">
                            <c:forEach items="${dailyDealHistories}" var="dailyDealHistory">
                                candleStickDataList.push([${dailyDealHistory.dealDateForTimestamp}, ${dailyDealHistory.startPrice}, ${dailyDealHistory.highPrice}, ${dailyDealHistory.lowPrice}, ${dailyDealHistory.closingPrice}]);
                                volumeList.push([${dailyDealHistory.dealDateForTimestamp}, ${dailyDealHistory.tradeVolume}]);
                                <c:if test="${dailyDealHistory.myAverageUnitPrice != 0}">
                                    myAverageUnitPriceList.push([${dailyDealHistory.dealDateForTimestamp},${dailyDealHistory.myAverageUnitPrice}]);
                                </c:if>
                                <c:if test="${dailyDealHistory.movingAverage.movingAverageMap.get('5') != null}">
                                    fiveMovingAverageList.push([${dailyDealHistory.dealDateForTimestamp},${dailyDealHistory.movingAverage.movingAverageMap.get('5')}]);
                                </c:if>
                                <c:if test="${dailyDealHistory.movingAverage.movingAverageMap.get('20') != null}">
                                    twentyMovingAverageList.push([${dailyDealHistory.dealDateForTimestamp},${dailyDealHistory.movingAverage.movingAverageMap.get('20')}]);
                                </c:if>
                                <c:if test="${dailyDealHistory.movingAverage.movingAverageMap.get('60') != null}">
                                    sixtyMovingAverageList.push([${dailyDealHistory.dealDateForTimestamp},${dailyDealHistory.movingAverage.movingAverageMap.get('60')}]);
                                </c:if>
                                <c:if test="${dailyDealHistory.movingAverage.movingAverageMap.get('120') != null}">
                                    oneTwentyMovingAverageList.push([${dailyDealHistory.dealDateForTimestamp},${dailyDealHistory.movingAverage.movingAverageMap.get('120')}]);
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
                                    text: '<b>범례의 평균단가를 클릭해서 없애면, 캔들차트가 더 잘 보입니다</b>',
                                    align: 'left'
                                },
                                chart: {
                                    zoomType: 'xy',
                                    events: {
                                        click: function (event) {
                                            // Get the quiz form element
                                            var modificationForm = document.getElementById('modifyCalculation');

                                            if(modificationForm){
                                                // First create a DIV element.
                                                var newModificationForm = document.createElement('div');
                                                newModificationForm.className = "input-group mb-3";

                                                // Then add the content (a new input box) of the element.
                                                newModificationForm.innerHTML =
                                                    "<div class='input-group mb-3'><span class='input-group-text' id='basic-addon1'>수정 날짜</span>"
                                                    + "<input type='date' class='form-control' name='modifyDate' placeholder='수정하실 날짜를 선택하세요' aria-label='modifyDate' aria-describedby='basic-addon1'></div>"
                                                    + "<div class='input-group mb-3'><span class='input-group-text' id='basic-addon2'>매도 비중%(현시점 보유비중의 몇%)</span>"
                                                    + "<input type='text' class='form-control' name='sellPercent' placeholder='% 제외하고 입력하세요(소수점 제외)' aria-label='sellPercent' aria-describedby='basic-addon2'>"
                                                    + "<span class='input-group-text' id='basic-addon3'>매도 가격</span>"
                                                    + "<input type='text' class='form-control' name='sellPrice' placeholder='매도하실 금액을 입력하세요(저가와 고가 사이)' aria-label='sellPrice' aria-describedby='basic-addon3'></div>"
                                                    + "<div class='input-group mb-3'><span class='input-group-text' id='basic-addon4'>매수 비중%(현시점 보유비중의 몇%)</span>"
                                                    + "<input type='text' class='form-control' name='buyPercent' placeholder='% 제외하고 입력하세요(소수점 제외)' aria-label='buyPercent' aria-describedby='basic-addon4'>"
                                                    + "<span class='input-group-text' id='basic-addon5'>매수 가격</span>"
                                                    + "<input type='text' class='form-control' name='buyPrice' placeholder='매수하실 금액을 입력하세요(저가와 고가 사이)' aria-label='buyPrice' aria-describedby='basic-addon5'></div>";

                                                // Finally put it where it is supposed to appear.
                                                document.getElementById("parentDivForModifiyCalculation").appendChild(newModificationForm);
                                            }
                                        }
                                    }
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
                                    height: '80%',
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
                                    split: true,
                                    style: {
                                        fontSize: '10px'
                                    }
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
                                    lineWidth: 4,
                                    onSeries: 'candle'
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
                <form id="modifyCalculation" action="buildup-calculate-modify" method="post" name="calculateRequestFrom">
                    <div class="px-4 py-5 my-5 text-center" id="parentDivForModifiyCalculation">
                        <div class="input-group mb-3" id= "modifyInputGroup">
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="basic-addon1">수정 날짜</span>
                                <input type="date" class="form-control" name="modifyDate" placeholder="수정하실 날짜를 선택하세요" aria-label="modifyDate" aria-describedby="basic-addon1">
                            </div>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="basic-addon2">매도 비중%(수정시점비중)</span>
                                <input type="text" class="form-control" name="sellPercent" placeholder="% 제외하고 입력하세요(소수점 제외)" aria-label="sellPercent" aria-describedby="basic-addon2">
                                <span class="input-group-text" id="basic-addon3">매도 가격</span>
                                <input type="text" class="form-control" name="sellPrice" placeholder="매도하실 금액을 입력하세요(저가와 고가 사이)" aria-label="sellPrice" aria-describedby="basic-addon3">
                            </div>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="basic-addon4">매수 비중%(수정시점비중)</span>
                                <input type="text" class="form-control" name="buyPercent" placeholder="% 제외하고 입력하세요(소수점 제외)" aria-label="buyPercent" aria-describedby="basic-addon4">
                                <span class="input-group-text" id="basic-addon5">매수 가격</span>
                                <input type="text" class="form-control" name="buyPrice" placeholder="매수하실 금액을 입력하세요(저가와 고가 사이)" aria-label="buyPrice" aria-describedby="basic-addon5">
                            </div>
                        </div>
                        <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
                            <button type="submit" class="btn btn-primary btn-lg px-4 gap-3" onclick="return">수정 전송</button>
                        </div>

                        <div class="row g-1 text-left" style="padding:20px">
                            <h5>그래프의 배경을 클릭하시면, 수정할 수 있는 행이 추가됩니다. </h5>
                            <p></p>
                            <h5>1개 행에 매수/매도 다 입력해도 되고, 한쪽만 입력해도 됩니다.</h5>
                            <h5>단, 매수/매도 비중을 입력했으면 매수/매도 단가도 필수입니다. 비중과 단가 중 하나라도 미입력 시 계산 무시합니다.</h5>
                            <h5>같은날짜에 더 매수/매도 하고 싶으시면, 행을 더 추가하시면 됩니다.</h5>
                            <h5>날짜가 없는 행은 계산에서 제외됩니다.</h5>
                            <h5>매도 시 매도금액의 0.3%를 수수료차원에서 실현손익에서 뺍니다.</h5>
                        </div>
                    </div>

                    <hr style="height:3px;color:#dc874f">
                    <div class="px-4 my-5 text-left">
                        <h2><strong>최초 입력 요청값</strong></h2>
                        <div style="padding:10px">
                            <div class="input-group mb-3">
                                <span class="input-group-text mb-3" id="basic-addon0">시뮬레이션 모드</span>
                                <select id="inputSimulationMode" class="form-select form-select-lg mb-3" name="simulationMode" aria-label="simulationMode" aria-describedby="basic-addon0"
                                 onfocus="this.initialSelect=this.selectedIndex;" onchange="this.selectedIndex=this.initialSelect;">
                                    <option value="dailyClosingPrice" selected>매일종가매수</option>
                                    <option value="minusCandle">음봉일 때만 매수</option>
                                </select>
                            </div>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="input-addon1">기업 이름</span>
                                <input readonly="true" type="text" id="inputCompanyName" class="form-control" name="companyName" aria-label="companyName" aria-describedby="input-addon1" value="">
                            </div>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="input-addon2">시작 날짜</span>
                                <input readonly="true" type="date" id="inputStartDate" class="form-control" name="startDate" aria-label="startDate" aria-describedby="input-addon2" value="">
                            </div>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="input-addon3">매도 날짜</span>
                                <input readonly="true" type="date" id="inputEndDate" class="form-control" name="endDate" aria-label="endDate" aria-describedby="input-addon3" value="">
                            </div>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="input-addon4">빌드업 금액</span>
                                <input readonly="true" type="text" id="inputBuildupAmount" class="form-control" name="buildupAmount" aria-label="buildupAmount" aria-describedby="input-addon4" value="">
                            </div>

                            <script>
                                $('#inputSimulationMode').val("${simulationMode}");
                                $('input[id=inputCompanyName]').attr('value',"${companyName}");
                                $('input[id=inputStartDate]').attr('value',"${startDate}");
                                $('input[id=inputEndDate]').attr('value',"${endDate}");
                                $('input[id=inputBuildupAmount]').attr('value',"${buildupAmount}");
                            </script>
                        </div>
                    </div>
                </form>

                <hr style="height:3px;color:#dc874f">
                <div class="px-4 my-5 text-left">
                    <h2><strong>매매 이력</strong></h2>
                    <div class="table-responsive">
                        <table class="table table-striped table-sm">
                            <thead>
                            <tr>
                                <th scope="col">거래일</th>
                                <th scope="col">종가</th>
                                <th scope="col">구매수량</th>
                                <th scope="col">내 평단</th>
                                <th scope="col">예수금</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${dailyDealHistoriesDesc}" var="dailyDealHistory">
                                <tr>
                                    <td>${dailyDealHistory.dealDate}</td>
                                    <td><fmt:formatNumber value="${dailyDealHistory.closingPrice}" pattern="#,###" /></td>
                                    <td><fmt:formatNumber value="${dailyDealHistory.closingPurchaseQuantity}" pattern="#,###" /></td>
                                    <td><fmt:formatNumber value="${dailyDealHistory.myAverageUnitPrice}" pattern="#,###" /></td>
                                    <td><fmt:formatNumber value="${dailyDealHistory.remainingAmount}" pattern="#,###" /></td>
                                </tr>
                            </c:forEach>
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
<script>
    //setting values
    var dealDateList = new Array();
    var closingPriceList = new Array();
    var myAverageUnitPriceList = new Array();
    var candleStickDataList = new Array();

    <c:if test="${isError == 'false'}">
        <c:forEach items="${dailyDealHistories}" var="dailyDealHistory">
            <c:if test="${dailyDealHistory.myAverageUnitPrice != 0}">
                dealDateList.push("${dailyDealHistory.dealDate}");
                closingPriceList.push("${dailyDealHistory.closingPrice}");
                myAverageUnitPriceList.push("${dailyDealHistory.myAverageUnitPrice}");

                candleStickDataList.push(["${dailyDealHistory.dealDate}", "${dailyDealHistory.startPrice}", "${dailyDealHistory.highPrice}", "${dailyDealHistory.lowPrice}", "${dailyDealHistory.closingPrice}"]);
            </c:if>
        </c:forEach>
    </c:if>

    // Graphs
    var ctx = document.getElementById('myChart')
    // eslint-disable-next-line no-unused-vars
    var myChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: dealDateList,
            datasets: [{
                label: "종가",
                data: closingPriceList,
                lineTension: 0.1,
                backgroundColor: 'transparent',
                borderColor: '#007bff',
                borderWidth: 2,
                pointBackgroundColor: '#007bff',
                fill: false,
                pointRadius: 0.5
            },{
                label: "내 평단",
                data: myAverageUnitPriceList,
                lineTension: 0.1,
                backgroundColor: 'transparent',
                borderColor: '#b22222',
                borderWidth: 1,
                pointBackgroundColor: '#b22222',
                fill: false,
                pointRadius: 0.5
            }]
        },
        options: {
            title: {
                display: true,
                text: '종가와 평균단가 그래프(${itemName})',
                fontSize: 24,
                fontColor: '#00443a'
            },
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: false
                    }
                }]
            },
            legend: {
                display: true
            }
        }
    })
</script>

</body>
</html>