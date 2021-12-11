<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="빌드업 계산기">
    <meta name="author" content="펭수르">
    <meta name="generator" content="stock-buildup 0.0.1">
    <title>빌드업 계산기</title>
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
    </style>
    <!-- Custom styles for this template -->
    <link href="/resources/css/buildup.css" rel="stylesheet">
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
                                <h1>빌드업 결과</h1>
                                <p></p>
                                <h2>수익률 : <fmt:formatNumber value="${earningRate}" pattern="#,###.00" />%</h2>
                                <h2>실현손익 : <fmt:formatNumber value="${earningAmount}" pattern="#,###" />원</h2>
                                <h2>총 투입금액 : <fmt:formatNumber value="${sumOfPurchaseAmount}" pattern="#,###" />원</h2>
                                <h2>총 매도금액 : <fmt:formatNumber value="${sumOfSellingAmount}" pattern="#,###" />원</h2>
                                <h2>총 매입수량 : <fmt:formatNumber value="${sumOfPurchaseQuantity}" pattern="#,###" />주</h2>
                                <h2>총 매도수량 : <fmt:formatNumber value="${sumOfSellingQuantity}" pattern="#,###" />주</h2>
                                <h2>총 매도수수료(0.3%) : <fmt:formatNumber value="${sumOfCommission}" pattern="#,###" />원</h2>
                                <h2>현재 평가금액 : <fmt:formatNumber value="${totalAmount}" pattern="#,###" />원</h2>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <hr style="height:3px;color:#dc874f">
                <canvas class="my-4 w-100" id="myChart" width="900" height="380"></canvas>

                <hr style="height:3px;color:#dc874f">
                <div id="container" style="height: 800px; min-width: 310px"></div>
                <script>
                    function drawCandleStickChart(){
                        //setting values
                        var candleStickDataList = [];
                        var volumeList = [];
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
                                myAverageUnitPriceList.push([${dailyDealHistory.dealDateForTimestamp},${dailyDealHistory.myAverageUnitPrice}]);
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
                                    text: '<b>범례의 평균단가를 클릭해서 없애면, 캔들차트가 더 잘 보입니다</b>',
                                    align: 'left'
                                },
                                chart: {
                                    zoomType: 'xy',
                                },
                                time:{
                                    useUTC: false,
                                    timezone: 'Asia/Seoul',
                                },
                                legend: {
                                    enabled: true,
                                    align: 'right',
                                    backgroundColor: '#FCFFC5',
                                    borderColor: 'black',
                                    borderWidth: 2,
                                    layout: 'vertical',
                                    verticalAlign: 'top',
                                    y: 100,
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
                                        text: '매수/매도 금액'
                                    },
                                    top: '85%',
                                    height: '15%',
                                    offset: 0,
                                    lineWidth: 2
                                }],
                                plotOptions: {
                                    candlestick: {
                                        downColor: 'blue',
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
                                    onSeries: 'candle'
                                }, {
                                    type: 'scatter',
                                    name: '추가 매수단가',
                                    data: additionalBuyingPrice,
                                    dataGrouping: {
                                        units: groupingUnits
                                    },
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
                                    type: 'column',
                                    id: 'buyingAmount',
                                    name: '매수 금액',
                                    data: additionalBuyingAmount,
                                    dataGrouping: {
                                        units: groupingUnits
                                    },
                                    yAxis: 2,
                                }, {
                                    type: 'column',
                                    name: '매도 금액',
                                    data: additionalSellingAmount,
                                    dataGrouping: {
                                        units: groupingUnits
                                    },
                                    yAxis: 2,
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
                        });
                    }
                    drawCandleStickChart();
                </script>
                <form id="modifyCalculation" action="buildup-calculate-modify" method="post" name="calculateRequestFrom">
                    <div class="px-4 py-5 my-5 text-center">
                        <h3>최초 입력 요청값</h3>
                        <div class="input-group mb-3">
                            <span class="input-group-text" id="input-addon1">기업 이름</span>
                            <input readonly="true" type="text" id="inputCompanyName" class="form-control" name="companyName" aria-label="companyName" aria-describedby="input-addon1" value="">
                            <span class="input-group-text" id="input-addon2">시작 날짜</span>
                            <input readonly="true" type="date" id="inputStartDate" class="form-control" name="startDate" aria-label="startDate" aria-describedby="input-addon2" value="">
                            <span class="input-group-text" id="input-addon3">매도 날짜</span>
                            <input readonly="true" type="date" id="inputEndDate" class="form-control" name="endDate" aria-label="endDate" aria-describedby="input-addon3" value="">
                            <span class="input-group-text" id="input-addon4">빌드업 금액</span>
                            <input readonly="true" type="text" id="inputBuildupAmount" class="form-control" name="buildupAmount" aria-label="buildupAmount" aria-describedby="input-addon4" value="">

                            <script>
                                $('input[id=inputCompanyName]').attr('value',"${companyName}");
                                $('input[id=inputStartDate]').attr('value',"${startDate}");
                                $('input[id=inputEndDate]').attr('value',"${endDate}");
                                $('input[id=inputBuildupAmount]').attr('value',"${buildupAmount}");
                            </script>
                        </div>
                    </div>
                </form>

                <hr style="height:3px;color:#dc874f">
                <h2>매매 이력</h2>
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
                        <c:forEach items="${dailyDealHistories}" var="dailyDealHistory">
                            <tr>
                                <td>${dailyDealHistory.dealDate}</td>
                                <td><fmt:formatNumber value="${dailyDealHistory.closingPrice}" pattern="#,###" /></td>
                                <td><fmt:formatNumber value="${dailyDealHistory.closingPurchaseQuantity}" pattern="#,###" /></td>
                                <td><fmt:formatNumber value="${dailyDealHistory.myAverageUnitPrice}" pattern="#,###" /></td>
                                <td><fmt:formatNumber value="${dailyDealHistory.additionalBuyingQuantity}" pattern="#,###" /></td>
                                <td><fmt:formatNumber value="${dailyDealHistory.additionalBuyingAmount}" pattern="#,###" /></td>
                                <td><fmt:formatNumber value="${dailyDealHistory.additionalSellingQuantity}" pattern="#,###" /></td>
                                <td><fmt:formatNumber value="${dailyDealHistory.additionalSellingAmount}" pattern="#,###" /></td>
                                <td><fmt:formatNumber value="${dailyDealHistory.commission}" pattern="#,###" /></td>
                                <td><fmt:formatNumber value="${dailyDealHistory.realizedEarningAmount}" pattern="#,###" /></td>
                                <td><fmt:formatNumber value="${dailyDealHistory.remainingAmount}" pattern="#,###" /></td>
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
<script>
    //setting values
    var dealDateList = new Array();
    var closingPriceList = new Array();
    var myAverageUnitPriceList = new Array();
    var candleStickDataList = new Array();

    <c:if test="${isError == 'false'}">
        <c:forEach items="${dailyDealHistories}" var="dailyDealHistory">
            dealDateList.push("${dailyDealHistory.dealDate}");
            closingPriceList.push("${dailyDealHistory.closingPrice}");
            myAverageUnitPriceList.push("${dailyDealHistory.myAverageUnitPrice}");

            candleStickDataList.push(["${dailyDealHistory.dealDate}", "${dailyDealHistory.startPrice}", "${dailyDealHistory.highPrice}", "${dailyDealHistory.lowPrice}", "${dailyDealHistory.closingPrice}"]);
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