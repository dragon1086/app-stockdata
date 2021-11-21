<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="빌드업 계산기">
    <meta name="author" content="펭수르">
    <meta name="generator" content="stock-buildup 0.0.1">
    <title>빌드업 계산기</title>
    <!--  부트스트랩 js 사용 -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script type="text/javascript" src="/js/bootstrap.js"></script>

    <link rel="stylesheet" href="/css/bootstrap.css">
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
    </style>
    <!-- Custom styles for this template -->
    <link href="/css/buildup.css" rel="stylesheet">
</head>
<body>
    <form action="buildup-calculate" method="get" name="calculateRequestFrom">
        <div class="px-4 py-5 my-5 text-center">
            <h1 class="display-5 fw-bold">오리지널 빌드업 시뮬레이션 계산기</h1>
            <div class="col-lg-6 mx-auto">
                <p class="lead mb-4">매일 종가로 주식을 매수하면, 투입금액 대비 얼마나 수익날 지 계산하는 프로그램입니다.</p>
                <p></p>
                <p class="lead mb-4">데이터는 지금 2000.01.01 ~ 2021.11.05 까지 있습니다. (수정주가 반영되어 있습니다.)</p>
                <p class="lead mb-4">차분히 템포조절하며 데이터를 2000년까지 쌓을 예정입니다.. 모두 성투하십쇼!</p>

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
                <span class="input-group-text" id="basic-addon4">빌드업 금액</span>
                <input type="text" class="form-control"  name="buildupAmount" placeholder="하루에 적립할 금액을 입력하세요" aria-label="buildupAmount" aria-describedby="basic-addon4">
            </div>

            <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
                <button type="submit" class="btn btn-primary btn-lg px-4 gap-3" onclick="return">전송</button>
            </div>
        </div>
    </form>

    <script type="text/javascript" src="/js/bootstrap.js"></script>
</body>
</html>
