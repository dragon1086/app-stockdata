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
                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                    <h1 class="h2">빌드업 결과</h1>
                    <div>
                        <c:choose>
                            <c:when test="${isError== 'true'}">
                                <h1>${errorMessage}</h1>
                            </c:when>
                            <c:otherwise>
                                <h1>수익률 : <fmt:formatNumber value="${earningRate}" pattern="#,###.00" />%</h1>
                                <h1>수익금액 : <fmt:formatNumber value="${earningAmount}" pattern="#,###" />원</h1>
                                <h1>총 투입금액 : <fmt:formatNumber value="${sumOfPurchaseAmount}" pattern="#,###" /></h1>
                                <h1>현재 평가금액 : <fmt:formatNumber value="${totalAmount}" pattern="#,###" />원</h1>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <canvas class="my-4 w-100" id="myChart" width="900" height="380"></canvas>

                <h2>매매 이력</h2>
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
                        <c:forEach items="${dailyDealHistories}" var="dailyDealHistory">
                            <tr>
                                <td>${dailyDealHistory.dealDate}</td>
                                <td>${dailyDealHistory.closingPrice}</td>
                                <td>${dailyDealHistory.purchaseQuantity}</td>
                                <td>${dailyDealHistory.myAverageUnitPrice}</td>
                                <td>${dailyDealHistory.buildupAmount}</td>
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
<script>
    //setting values
    var dealDateList = new Array();
    var closingPriceList = new Array();
    var myAverageUnitPriceList = new Array();
    <c:if test="${isError == 'false'}">
        <c:forEach items="${dailyDealHistories}" var="dailyDealHistory">
            dealDateList.push("${dailyDealHistory.dealDate}");
            closingPriceList.push("${dailyDealHistory.closingPrice}");
            myAverageUnitPriceList.push("${dailyDealHistory.myAverageUnitPrice}");
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
                lineTension: 0,
                backgroundColor: 'transparent',
                borderColor: '#007bff',
                borderWidth: 4,
                pointBackgroundColor: '#007bff'
            },{
                label: "내 평단",
                data: myAverageUnitPriceList,
                lineTension: 0,
                backgroundColor: 'transparent',
                borderColor: '#b22222',
                borderWidth: 2,
                pointBackgroundColor: '#b22222'
            }]
        },
        options: {
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