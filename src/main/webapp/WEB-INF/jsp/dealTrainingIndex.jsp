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
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <link rel="stylesheet" href="//code.jquery.com/ui/1.13.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/ui/1.13.1/jquery-ui.js"></script>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">

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
            height: 38px !important; /* 버튼 높이를 명시적으로 지정 */
            line-height: 1.5 !important; /* 텍스트 라인 높이 지정 */
        }

        .btn-auto-sim:hover,
        .btn-outline-light:hover {
            background-color: #ffffff !important;
            color: #3498db !important;
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

        function openDealHistoryTab() {
            window.open('/deal-calculate-histories', '_blank');
        }

        // 툴팁 초기화 함수
        function initTooltips() {
            var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
            tooltipTriggerList.forEach(function (tooltipTriggerEl) {
                new bootstrap.Tooltip(tooltipTriggerEl)
            })
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
                delay: 500,    //검색창에 글자 써지고 나서 autocomplete 창 뜰 때 까지 딜레이 시간(ms)
                select: function(event, ui) {
                    console.log("Selected: " + ui.item.value);
                }
            });

            initTooltips();
        });
    </script>

</head>
<body>
    <c:if test="${not empty errorMessage}">
        <div style="color: red; background-color: #ffe6e6; padding: 10px; border: 1px solid #ff9999; margin-bottom: 15px;">
            <c:out value="${errorMessage}" />
        </div>
    </c:if>

    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg fixed-top navbar-dark">
        <div class="container">
            <a class="navbar-brand" href="/">주식 매매 시뮬레이션</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="btn-auto-sim" href="/buildup">자동 시뮬레이션으로 이동</a>
                    </li>
                </ul>
                <div class="d-flex">
                    <% if (session.getAttribute("sessionUser") == null) { %>
                    <button class="btn btn-outline-light" onclick="location.href='/login/google'">Google로 로그인</button>
                    <% } else { %>
                    <span class="navbar-text text-light me-3">환영합니다, ${sessionScope.sessionUser.email}님!</span>
                    <button class="btn btn-outline-light me-2" onclick="location.href='/logout/google'">로그아웃</button>
                    <button class="btn btn-info" onclick="openDealHistoryTab()">시뮬레이션 이어하기</button>
                    <% } %>
                </div>
            </div>
        </div>
    </nav>

    <!-- Main Content -->
    <div class="container main-container">
        <h1 class="text-center mb-4">주식 매매 시뮬레이션</h1>

        <form action="deal-calculate" method="post" name="calculateRequestFrom">
            <div class="mb-4">
                <label for="companyNameInput" class="form-label d-flex align-items-center">
                    기업 이름
                    <i class="bi bi-question-circle-fill tooltip-icon" data-bs-toggle="tooltip" data-bs-placement="right" title="시뮬레이션할 기업을 선택합니다. 기본적으로 랜덤으로 선택되며, 체크박스를 선택하면 직접 입력할 수 있습니다."></i>
                </label>
                <div class="input-group">
                    <input type="text" id="companyNameInput" class="form-control" name="companyName" placeholder="기업 이름" disabled>
                    <div class="input-group-text">
                        <input class="form-check-input mt-0" type="checkbox" id="companyNameCheck" onclick="companyNameOnOff()">
                    </div>
                </div>
            </div>

            <div class="mb-4">
                <label for="slotAmount" class="form-label d-flex align-items-center">
                    종목에 배분할 금액
                    <i class="bi bi-question-circle-fill tooltip-icon" data-bs-toggle="tooltip" data-bs-placement="right" title="이 종목에 투자할 총 금액을 입력합니다. 이 금액을 기준으로 매매가 이루어집니다."></i>
                </label>
                <input type="text" class="form-control" id="slotAmount" name="slotAmount" placeholder="배분할 총금액을 입력하세요">
            </div>

            <div class="mb-4">
                <label for="portion" class="form-label d-flex align-items-center">
                    시작 비중(%)
                    <i class="bi bi-question-circle-fill tooltip-icon" data-bs-toggle="tooltip" data-bs-placement="right" title="초기 투자 비중을 설정합니다. 0%부터 100%까지 설정 가능하며, 0%는 현금 보유 상태, 100%는 풀 매수 상태를 의미합니다."></i>
                </label>
                <input type="text" class="form-control" id="portion" name="portion" placeholder="% 제외하고 입력하세요(소수점 제외)">
            </div>

            <div class="mb-4">
                <label for="startDate" class="form-label d-flex align-items-center">
                    시작 날짜
                    <i class="bi bi-question-circle-fill tooltip-icon" data-bs-toggle="tooltip" data-bs-placement="right" title="시뮬레이션 시작 날짜를 선택합니다. 기본적으로 랜덤으로 선택되며, 체크박스를 선택하면 직접 입력할 수 있습니다."></i>
                </label>
                <div class="input-group">
                    <input type="date" id="startDate" class="form-control" name="startDate" disabled>
                    <div class="input-group-text">
                        <input class="form-check-input mt-0" type="checkbox" id="startDateCheck" onclick="startDateOnOff()">
                    </div>
                </div>
            </div>

            <div class="mb-4">
                <label for="valuationPercent" class="form-label d-flex align-items-center">
                    시작 평가손익(%)
                    <i class="bi bi-question-circle-fill tooltip-icon" data-bs-toggle="tooltip" data-bs-placement="right" title="초기 평가손익 상태를 설정합니다. 기본적으로 랜덤으로 선택되며, 체크박스를 선택하면 직접 입력할 수 있습니다."></i>
                </label>
                <div class="input-group">
                    <input type="text" id="valuationPercent" class="form-control" name="valuationPercent" placeholder="초기 평가손익" disabled>
                    <div class="input-group-text">
                        <input class="form-check-input mt-0" type="checkbox" id="valuationPercentCheck" onclick="valuationPercentOnOff()">
                    </div>
                </div>
            </div>

            <div class="mb-4">
                <label for="level" class="form-label d-flex align-items-center">
                    난이도 선택
                    <i class="bi bi-question-circle-fill tooltip-icon" data-bs-toggle="tooltip" data-bs-placement="right" title="시뮬레이션의 난이도를 선택합니다. 초급은 -20%~+20%, 중급은 -50%~+50%, 고급은 -80%~+80%의 범위에서 시작 평가손익이 설정됩니다."></i>
                </label>
                <select class="form-select" id="level" name="level">
                    <option value="beginner" selected>초급 (-20%~+20%)</option>
                    <option value="intermediate">중급 (-50%~+50%)</option>
                    <option value="master">고급 (-80%~+80%)</option>
                </select>
            </div>

            <div class="d-grid">
                <button type="submit" class="btn btn-primary btn-lg">시뮬레이션 시작</button>
            </div>
        </form>

        <hr class="my-4">

        <div class="text-center">
            <button type="button" class="btn btn-info" onclick="location.href='/dealTrainingManual'">
                시뮬레이션 메뉴얼
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

    <!-- Footer -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
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
