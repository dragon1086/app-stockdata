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
    <meta name="description" content="주식 시뮬레이션">
    <meta name="author" content="펭수르">
    <meta name="generator" content="stock-buildup 0.0.1">
    <meta name="google-site-verification" content="8TTVWhEDuj8mMlx0fjRf-TGRRHi4qA7HnI_4cCEbFXQ" />
    <title>주식 시뮬레이션</title>
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

        function startDateOnOff(){
            var startDateCheckbox = document.getElementById("startDateCheck");


            if (startDateCheckbox.checked == true){
                document.getElementById("startDate").disabled = false;
            } else {
                document.getElementById("startDate").disabled = true;
            }
        }

        function valuationPercentOnOff(){
            var valuationPercentCheckbox = document.getElementById("valuationPercentCheck");


            if (valuationPercentCheckbox.checked == true){
                document.getElementById("valuationPercent").disabled = false;
            } else {
                document.getElementById("valuationPercent").disabled = true;
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
        <button type="button" class="btn btn-secondary" onclick="location.href='/buildup'" style="height:50px;width:330px;font-size:18px;">빌드업 자동 시뮬레이션 하러 가기</button>
    </div>
    <form action="deal-calculate" method="post" name="calculateRequestFrom">
        <div class="px-4 py-5 my-5 text-center">
            <h1 class="display-3 fw-bold">과거 일봉차트 매매 시뮬레이션</h1>
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon1">기업 이름(기본 랜덤)</span>
                <input type="text" id="companyNameInput" class="form-control"  name="companyName" placeholder="기업 랜덤 선택. 기업명을 입력하시려면 오른쪽 체크박스 체크." aria-label="companyName" aria-describedby="basic-addon1" disabled>
                <input type="checkbox" id="companyNameCheck" name="companyNameCheck" onclick="companyNameOnOff()">
                <label for="companyNameCheck" style="margin: auto">기업이름(기본 랜덤)</label>
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon2">종목에 배분할 금액</span>
                <input type="text" class="form-control"  name="slotAmount" placeholder="배분할 총금액을 입력하세요" aria-label="slotAmount" aria-describedby="basic-addon2">
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon3">시작 비중(%)</span>
                <input type="text" class="form-control"  name="portion" placeholder="% 제외하고 입력하세요(소수점 제외)" aria-label="portion" aria-describedby="basic-addon3">
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon4">시작 날짜(기본 랜덤)</span>
                <input type="date" id="startDate" class="form-control"  name="startDate" placeholder="랜덤 날짜. 훈련 시작 날짜를 입력하시려면 오른쪽 체크박스 체크." aria-label="startDate" aria-describedby="basic-addon4" disabled>
                <input type="checkbox" id="startDateCheck" name="startDateCheck" onclick="startDateOnOff()">
                <label for="startDateCheck" style="margin: auto">시작일 설정(기본 랜덤)</label>
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon5">시작 평가손익(%)(기본 랜덤)</span>
                <input type="text" id="valuationPercent" class="form-control"  name="valuationPercent" placeholder="랜덤 평가손익. 초기 평가손익을 입력하시려면 오른쪽 체크박스 체크." aria-label="valuationPercent" aria-describedby="basic-addon5" disabled>
                <input type="checkbox" id="valuationPercentCheck" name="valuationPercentCheck" onclick="valuationPercentOnOff()">
                <label for="valuationPercentCheck" style="margin: auto">시작평가손익 설정(기본 랜덤)</label>
            </div>
            <div class="input-group mb-3" style="margin-top: 25px;">
                <span class="input-group-text mb-1" id="basic-addon0">난이도 선택(시작비중 있을때만 적용)</span>
                <select class="form-select form-select-lg mb-1" name="level" aria-label="level" aria-describedby="basic-addon0">
                    <option value="beginner" selected>-20%~+20%</option>
                    <option value="intermediate">-50%~+50%</option>
                    <option value="master">-80%~+80%</option>
                </select>
            </div>

            <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
                <button type="submit" class="btn btn-primary btn-lg px-4 gap-3" onclick="return">시뮬레이션 시작</button>
            </div>
        </div>
    </form>

    <div class="display-3" style="margin-left:20px">
        <h1 class="lead mb-4 fw-bold" style="font-size:20px;">기능 개요</h1>
        <p class="lead mb-4" style="font-size:17px;">일봉 기준으로 종목을 관찰하고 매매를 시뮬레이션 해보는 프로그램입니다.</p>
        <p class="lead mb-4" style="font-size:17px;">아이디어는 어니스트와 주식빌드업 유튜브 채널을 참고했습니다.</p>
        <p class="lead mb-4" style="font-size:17px;">(자세한 메뉴얼이 필요하시면 아래 메뉴얼 버튼을 클릭해주세요!)</p>
        <p class="lead mb-4" style="font-size:17px;">하루하루 다음 일봉을 예상해보며, 매매와 기다림을 선택해가며 주어진 환경에서 실현수익을 만드는 것이 목표입니다.</p>
        <p></p>
        <p class="lead mb-4" style="font-size:17px;">"종목에 배분할 총금액", "시작 비중"을 입력하면, 랜덤하게 종목을 선택하고 3년치의 일봉차트가 보여집니다.</p>
        <p class="lead mb-4" style="font-size:17px;">일봉차트를 참고하셔서 어떤 상황에서도 수익을 만들어보는 경험을 체험해보세요!</p>
        <p class="lead mb-4" style="font-size:17px;">(종목이 마음에 안드시면, 살짝 뒤로가기 ㅎㅎ)</p>
        <p></p>
        <p class="lead mb-4" style="font-size:17px;">시작 시점, 평균 단가, 평가 손익은 랜덤으로 정해집니다.</p>
        <p class="lead mb-4" style="font-size:17px;">평가 손익은 난이도에 따라 다르게 설정되며, 여러분이 과거 특정 시점부터 관리해온 손익이라고 가정했습니다.</p>
        <p class="lead mb-4" style="font-size:17px;">만약 "시작 비중"을 0으로 입력하면, 난이도에 상관없이 처음부터 새로 시작할 수 있습니다.</p>
        <p></p>
        <p class="lead mb-4" style="font-size:17px;">시작비중이 0 이상이고, 평가손익을 랜덤으로 설정할 경우 난이도를 설정할 수 있습니다</p>
        <p class="lead mb-4" style="font-size:17px;">난이도는 올라갈수록 평가 손익의 복불복 범위를 크게 설정했습니다.</p>
        <p class="lead mb-4" style="font-size:17px;">난이도를 "-20% ~ +20%"로 선택하시면 평가손익의 범위는 -20% ~ +20% 입니다.</p>
        <p class="lead mb-4" style="font-size:17px;">난이도를 "-50% ~ +50%"로 선택하시면 평가손익의 범위는 -50% ~ +50% 입니다.</p>
        <p class="lead mb-4" style="font-size:17px;">난이도를 "-80% ~ +80%"로 선택하시면 평가손익의 범위는 -80% ~ +80% 입니다.</p>
        <p></p>

    </div>
    <div class="px-4 py-5 my-5 text-center">
        <button type="button" class="btn btn-info" onclick="location.href='/dealTrainingManual'" style="height:30px;width:280px;font-size:14px;">상추 일봉매매 시뮬레이션 메뉴얼</button>
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
