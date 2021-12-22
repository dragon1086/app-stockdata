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
    <meta name="description" content="일봉 매매 훈련보조기">
    <meta name="author" content="펭수르">
    <meta name="generator" content="stock-buildup 0.0.1">
    <title>일봉 매매 훈련보조기</title>
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
    </style>
    <!-- Custom styles for this template -->
    <link href="/resources/css/buildup.css" rel="stylesheet">
</head>
<body>
    <form action="deal-calculate" method="post" name="calculateRequestFrom">
        <div class="px-4 py-5 my-5 text-center">
            <h1 class="display-5 fw-bold">일봉매매 시뮬레이션 계산기</h1>
            <div class="display-5 mx-auto">
                <p class="lead mb-4">일봉 기준으로 종목을 관찰하고 매매를 시뮬레이션 해보는 프로그램입니다.</p>
                <p class="lead mb-4">"종목", "종목에 배분할 총금액", "시작 비중"을 선택하면, 랜덤하게 3년치의 일봉차트가 보여집니다.</p>
                <p class="lead mb-4">시작 시 평가손익은 -50%에서 +50% 사이에 랜덤으로 배정됩니다.</p>
                <p class="lead mb-4">하루하루 다음 일봉을 예상해보며, 매매와 기다림을 선택해가며 최종적으로 실현수익으로 만드는 것이 목표입니다.</p>
                <p></p>
                <p class="lead mb-4">데이터는 지금 2000.01.01 ~ 2021.12.03 까지 있습니다. (수정주가 반영되어 있습니다.)</p>

            </div>
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon1">기업 이름</span>
                <input type="text" class="form-control"  name="companyName" placeholder="기업명을 입력하세요" aria-label="companyName" aria-describedby="basic-addon1">
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon2">종목에 배분할 금액</span>
                <input type="text" class="form-control"  name="slotAmount" placeholder="배분할 총금액을 입력하세요" aria-label="slotAmount" aria-describedby="basic-addon2">
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon3">시작 비중(%)</span>
                <input type="text" class="form-control"  name="portion" placeholder="% 제외하고 입력하세요(소수점 제외)" aria-label="portion" aria-describedby="basic-addon3">
            </div>

            <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
                <button type="submit" class="btn btn-primary btn-lg px-4 gap-3" onclick="return">훈련 시작</button>
            </div>
        </div>
    </form>

    <script type="text/javascript" src="/resources/js/bootstrap.js"></script>
</body>
</html>
