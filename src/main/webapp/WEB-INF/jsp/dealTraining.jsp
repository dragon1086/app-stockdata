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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">

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
        .status-value.bold {
            font-weight: bold;
        }

        .status-value.profit {
            color: #ff0000;  /* 빨간색 */
        }

        .status-value.loss {
            color: #0000ff;  /* 파란색 */
        }
    </style>

    <!-- Custom styles for this template -->
    <link href="/resources/css/buildup.css" rel="stylesheet">
    <script src="/resources/js/dealTraining.min.js"></script>
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

        function setDealModifications() {
            <c:forEach items="${dealModifications}" var="dealModification">
            dealModifications.push(${dealModification});
            </c:forEach>
        }
        function setDealHistories() {
            <c:forEach items="${dailyDealHistories}" var="dailyDealHistory">
            dailyDealHistories.push(${dailyDealHistory});
            </c:forEach>
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
                    <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
                        <div class="col"><div class="status-item"><span class="status-label">실현수익률:</span> <span class="status-value earningRate important-value profit-loss" data-unit="%"></span></div></div>
                        <div class="col"><div class="status-item"><span class="status-label">실현손익:</span> <span class="status-value earningAmount important-value profit-loss" data-unit="원"></span></div></div>
                        <div class="col"><div class="status-item"><span class="status-label">현재 평가손익:</span> <span class="status-value valuationPercent important-value profit-loss" data-unit="%"></span></div></div>
                        <div class="col"><div class="status-item"><span class="status-label">현재 평가금액:</span> <span class="status-value totalAmount" data-unit="원"></span></div></div>
                        <div class="col"><div class="status-item"><span class="status-label">현재 평균단가:</span> <span class="status-value averageUnitPrice" data-unit="원"></span></div></div>
                        <div class="col"><div class="status-item"><span class="status-label">현재 비중:</span> <span class="status-value portion" data-unit="%"></span></div></div>
                        <div class="col"><div class="status-item"><span class="status-label">슬랏 할당금액:</span> <span class="status-value slotAmount" data-unit="원"></span></div></div>
                        <div class="col"><div class="status-item"><span class="status-label">슬랏 예수금:</span> <span class="status-value remainingSlotAmount" data-unit="원"></span></div></div>
                        <div class="col"><div class="status-item"><span class="status-label">슬랏 예수금 비중:</span> <span class="status-value remainingPortion" data-unit="%"></span></div></div>
                        <div class="col"><div class="status-item"><span class="status-label">총 매입금액:</span> <span class="status-value sumOfPurchaseAmount" data-unit="원"></span></div></div>
                        <div class="col"><div class="status-item"><span class="status-label">총 매도금액:</span> <span class="status-value sumOfSellingAmount" data-unit="원"></span></div></div>
                        <div class="col"><div class="status-item"><span class="status-label">총 매입수량:</span> <span class="status-value sumOfPurchaseQuantity" data-unit="주"></span></div></div>
                        <div class="col"><div class="status-item"><span class="status-label">총 매도수량:</span> <span class="status-value sumOfSellingQuantity" data-unit="주"></span></div></div>
                        <div class="col"><div class="status-item"><span class="status-label">총 매도수수료(0.3%):</span> <span class="status-value sumOfCommission" data-unit="원"></span></div></div>
                        <div class="col"><div class="status-item"><span class="status-label">현재 종가:</span> <span class="status-value currentClosingPrice" data-unit="원"></span></div></div>
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