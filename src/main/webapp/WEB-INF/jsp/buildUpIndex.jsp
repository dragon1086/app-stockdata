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
    <!-- Google AdSense -->
    <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-3126725853648403"
            crossorigin="anonymous"></script>
    <!-- End Google AdSense -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="빌드업 시뮬레이션기">
    <meta name="author" content="펭수르">
    <meta name="generator" content="stock-buildup 0.0.2">
    <title>빌드업 시뮬레이션기</title>
    <!--  부트스트랩 js 사용 -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script type="text/javascript" src="/resources/js/bootstrap.js"></script>

    <link rel="stylesheet" href="/resources/css/bootstrap.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <link rel="stylesheet" href="//code.jquery.com/ui/1.13.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/ui/1.13.1/jquery-ui.js"></script>

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

        function companyNameOnOff(){
            var companyNameCheckbox = document.getElementById("companyNameCheck");


            if (companyNameCheckbox.checked == true){
                document.getElementById("companyNameInput").disabled = false;
            } else {
                document.getElementById("companyNameInput").disabled = true;
            }
        }

        $(function() {    //화면 다 뜨면 시작
            $("#companyNameInput").autocomplete({
                source : function( request, response ) {
                    $.ajax({
                        type: 'post',
                        url: "/company",
                        dataType: "json",
                        data: {"keyword":$("#companyNameInput").val()},
                        success: function(data) {
                            //서버에서 json 데이터 response 후 목록에 추가
                            response(
                                $.map(data, function(item) {    //json[i] 번째 에 있는게 item 임.
                                    return {
                                        label: item,
                                        value: item,
                                    }
                                })
                            );
                        }
                    });
                },    // source 는 자동 완성 대상
                focus : function(event, ui) {
                    return false;//한글 아이템 선택 시, 글자가 사라지는 버그 잡아줌
                },
                minLength: 1,// 최소 글자수
                autoFocus: true, //첫번째 항목 자동 포커스 기본값 false
                classes: {
                    "ui-autocomplete": "highlight"
                },
                delay: 1000,    //검색창에 글자 써지고 나서 autocomplete 창 뜰 때 까지 딜레이 시간(ms)
                disabled: false, //자동완성 기능 스위치
                position: { my : "left top", at: "left bottom" }
            });
        });
    </script>
</head>
<body>
    <div class="px-4 py-5 my-5 text-center">
        <button type="button" class="btn btn-secondary" onclick="location.href='/'" style="height:50px;width:370px;font-size:18px;">과거 일봉차트 매매 시뮬레이션 하러 가기</button>
    </div>
    <form action="buildup-calculate" method="post" name="calculateRequestFrom">
        <div class="px-4 py-5 my-5 text-center">
            <h1 class="display-3 fw-bold">빌드업 자동 시뮬레이션</h1>
            <div class="input-group mb-3" style="margin-top: 25px;">
                <span class="input-group-text mb-3" id="basic-addon0">시뮬레이션 모드</span>
                <select class="form-select form-select-lg mb-3" name="simulationMode" aria-label="simulationMode" aria-describedby="basic-addon0">
                    <option value="dailyClosingPrice" selected>매일종가매수</option>
                    <option value="minusCandle">음봉일 때만 매수</option>
                </select>
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon1">기업 이름(기본 랜덤)</span>
                <input type="text" id="companyNameInput" class="form-control"  name="companyName" placeholder="기업명을 입력하세요" aria-label="companyName" aria-describedby="basic-addon1" disabled>
                <input type="checkbox" id="companyNameCheck" name="companyNameCheck" onclick="companyNameOnOff()">
                <label for="companyNameCheck" style="margin: auto">기업이름(기본 랜덤)</label>
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
                <button type="submit" class="btn btn-primary btn-lg px-4 gap-3" onclick="return">시뮬레이션 시작</button>
            </div>
        </div>
    </form>
    <div class="display-3" style="margin-left:20px">
        <h1 class="lead mb-4 fw-bold" style="font-size:20px;">기능 개요</h1>
        <p class="lead mb-4 "style="font-size:17px;">일정한 규칙을 정해서 매수/매도를 하면, 투입금액 대비 얼마나 수익날 지 시뮬레이션하는 프로그램입니다.</p>
        <p class="lead mb-4 "style="font-size:17px;">아이디어는 어니스트와 주식빌드업 유튜브 채널을 참고했습니다.</p>
        <p class="lead mb-4" style="font-size:17px;">(자세한 메뉴얼이 필요하시면 아래 메뉴얼 버튼을 클릭해주세요!)</p>
        <p class="lead mb-4 "style="font-size:17px;">시뮬레이션 모드는 현재 2가지가 있습니다.</p>
        <p class="lead mb-4 "style="font-size:17px;">1. 매일종가매수 및 마지막날 전량매도</p>
        <p class="lead mb-4 "style="font-size:17px;">2. 음봉일때만 매수 및 마지막날 전량매도</p>
        <p></p>

    </div>
    <div class="px-4 py-5 my-5 text-center">
        <button type="button" class="btn btn-info" onclick="location.href='/buildupManual'" style="height:30px;width:280px;font-size:14px;">빌드업 자동 시뮬레이션 메뉴얼</button>
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

    <script type="text/javascript" src="/resources/js/bootstrap.js"></script>
</body>
</html>
