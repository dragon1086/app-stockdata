<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
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
    <meta name="description" content="룰 적용 시뮬레이션기">
    <meta name="author" content="펭수르">
    <meta name="generator" content="stock-buildup 0.0.2">
    <title>매일종가매수 시뮬레이션기</title>
    <!--  부트스트랩 js 사용 -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script type="text/javascript" src="/resources/js/bootstrap.js"></script>

    <link rel="stylesheet" href="/resources/css/bootstrap.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

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
    <div class="px-4 py-5 my-5 text-center">
        <button type="button" class="btn btn-info" onclick="location.href='/buildupManual'" style="height:30px;width:280px;font-size:14px;">매일종가매수 시뮬레이션 메뉴얼</button>
    </div>
    <div class="px-4 py-5 my-5 text-center">
        <button type="button" class="btn btn-secondary" onclick="location.href='/'" style="height:50px;width:250px;font-size:18px;">일봉차트 매매훈련하러 가기</button>
    </div>
    <form action="buildup-calculate" method="post" name="calculateRequestFrom">
        <div class="px-4 py-5 my-5 text-center">
            <h1 class="display-3 fw-bold">룰 적용 시뮬레이션기</h1>
            <div class="display-3 mx-auto">
                <p class="lead mb-4 "style="font-size:17px;">일정한 규칙을 정해서 매수/매도를 하면, 투입금액 대비 얼마나 수익날 지 시뮬레이션하는 프로그램입니다.</p>
                <p class="lead mb-4 "style="font-size:17px;">시뮬레이션 모드는 현재 2가지가 있습니다.</p>
                <p class="lead mb-4 "style="font-size:17px;">1. 매일종가매수 및 마지막날 전량매도</p>
                <p class="lead mb-4 "style="font-size:17px;">2. 음봉일때만 매수 및 마지막날 전량매도</p>
                <p></p>
                <p class="lead mb-4 "style="font-size:17px;">데이터는 지금 2000.01.01 ~ 2022.02.04 까지 있습니다. (수정주가 반영되어 있습니다.)</p>

            </div>
            <div class="input-group mb-3">
                <span class="input-group-text mb-3" id="basic-addon0">시뮬레이션 모드</span>
                <select class="form-select form-select-lg mb-3" name="simulationMode" aria-label="simulationMode" aria-describedby="basic-addon0">
                    <option value="dailyClosingPrice" selected>매일종가매수</option>
                    <option value="minusCandle">음봉일 때만 매수</option>
                </select>
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon1">기업 이름</span>
                <input type="text" class="form-control"  name="companyName" placeholder="기업명을 입력하세요" aria-label="companyName" aria-describedby="basic-addon1">
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon2">시작 날짜</span>
                <input type="date" class="form-control"  name="startDate" placeholder="startDate" aria-label="startDate" aria-describedby="basic-addon2">
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon3">매도 날짜</span>
                <input type="date" class="form-control"  name="endDate" placeholder="endDate" aria-label="endDate" aria-describedby="basic-addon3">
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon4">매일 매수 할 금액</span>
                <input type="text" class="form-control"  name="buildupAmount" placeholder="하루에 적립할 금액을 입력하세요" aria-label="buildupAmount" aria-describedby="basic-addon4">
            </div>

            <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
                <button type="submit" class="btn btn-primary btn-lg px-4 gap-3" onclick="return">전송</button>
            </div>
        </div>
    </form>

    <script type="text/javascript" src="/resources/js/bootstrap.js"></script>
</body>
</html>
