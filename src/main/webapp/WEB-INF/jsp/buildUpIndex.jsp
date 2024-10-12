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
<%--    <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-3126725853648403"--%>
<%--            crossorigin="anonymous"></script>--%>
    <!-- End Google AdSense -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="자동 시뮬레이션기">
    <meta name="author" content="펭수르">
    <meta name="generator" content="stock-buildup 0.0.2">
    <title>자동 시뮬레이션기</title>
    <!--  부트스트랩 js 사용 -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <link rel="stylesheet" href="//code.jquery.com/ui/1.13.1/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.13.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/ui/1.13.1/jquery-ui.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

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

        .btn-manual,
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
        .btn-manual:hover,
        .btn-outline-light:hover {
            background-color: #ffffff !important;
            color: #3498db !important;
        }

        .btn-home i,
        .btn-manual i {
            margin-right: 0.5rem;
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
    </style>
    <script>
        function companyNameOnOff(){
            var companyNameCheckbox = document.getElementById("companyNameCheck");
            document.getElementById("companyNameInput").disabled = !companyNameCheckbox.checked;
        }

        function initTooltips() {
            var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
            var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
                return new bootstrap.Tooltip(tooltipTriggerEl)
            })
        }

        $(function() {    //화면 다 뜨면 시작
            document.cookie = "SameSite=None; Secure";

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
            })

            // Bootstrap이 로드된 후 tooltip 초기화
            $(window).on('load', function() {
                initTooltips();
            });
        });
    </script>
</head>
<body>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg fixed-top navbar-dark">
        <div class="container">
            <a class="navbar-brand" href="/">자동 시뮬레이션</a>
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
                        <a class="btn-manual" href="/buildupManual">
                            <i class="fas fa-book"></i>
                            <span>매뉴얼</span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <% if (session.getAttribute("sessionUser") == null) { %>
                        <button class="btn btn-outline-light" onclick="location.href='/login/google'">Google로 로그인</button>
                        <% } else { %>
                        <button class="btn btn-outline-light me-2" onclick="location.href='/logout/google'">로그아웃</button>
                        <% } %>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- 메인 컨테이너 -->
    <div class="container main-container">
        <h1 class="text-center mb-4">자동 시뮬레이션
            <i class="fas fa-question-circle tooltip-icon"
               data-bs-toggle="tooltip"
               data-bs-placement="right"
               title="일정한 규칙을 정해서 매수/매도를 하면, 투입금액 대비 얼마나 수익날 지 시뮬레이션하는 프로그램입니다. 현재 '매일종가매수 및 마지막날 전량매도'와 '음봉일때만 매수 및 마지막날 전량매도' 두 가지 모드가 있습니다."></i>
        </h1>

        <form action="buildup-calculate" method="post" name="calculateRequestFrom">
            <div class="mb-4">
                <label for="simulationMode" class="form-label d-flex align-items-center">
                    시뮬레이션 모드
                    <i class="fas fa-question-circle tooltip-icon"
                       data-bs-toggle="tooltip"
                       data-bs-placement="right"
                       title="시뮬레이션 규칙을 선택합니다. '매일종가매수'는 매일 종가에 매수하고, '음봉일 때만 매수'는 주가가 하락한 날에만 매수합니다."></i>
                </label>
                <select class="form-select" id="simulationMode" name="simulationMode">
                    <option value="dailyClosingPrice" selected>매일종가매수</option>
                    <option value="minusCandle">음봉일 때만 매수</option>
                </select>
            </div>

            <div class="mb-4">
                <label for="companyNameInput" class="form-label d-flex align-items-center">
                    기업 이름
                    <i class="fas fa-question-circle tooltip-icon"
                       data-bs-toggle="tooltip"
                       data-bs-placement="right"
                       title="시뮬레이션할 기업을 선택합니다. 기본적으로 랜덤으로 선택되며, 체크박스를 선택하면 직접 입력할 수 있습니다."></i>
                </label>
                <div class="input-group">
                    <input type="text" id="companyNameInput" class="form-control" name="companyName" placeholder="기업 이름" disabled>
                    <div class="input-group-text">
                        <input class="form-check-input mt-0" type="checkbox" id="companyNameCheck" onclick="companyNameOnOff()">
                    </div>
                </div>
            </div>

            <div class="mb-4">
                <label for="startDate" class="form-label d-flex align-items-center">
                    시작 날짜
                    <i class="fas fa-question-circle tooltip-icon"
                       data-bs-toggle="tooltip"
                       data-bs-placement="right"
                       title="시뮬레이션 시작 날짜를 선택합니다."></i>
                </label>
                <input type="date" class="form-control" id="startDate" name="startDate">
            </div>

            <div class="mb-4">
                <label for="endDate" class="form-label d-flex align-items-center">
                    매도 날짜
                    <i class="fas fa-question-circle tooltip-icon"
                       data-bs-toggle="tooltip"
                       data-bs-placement="right"
                       title="시뮬레이션 종료 및 전량 매도 날짜를 선택합니다."></i>
                </label>
                <input type="date" class="form-control" id="endDate" name="endDate">
            </div>

            <div class="mb-4">
                <label for="buildupAmount" class="form-label d-flex align-items-center">
                    매일 매수 할 금액
                    <i class="fas fa-question-circle tooltip-icon"
                       data-bs-toggle="tooltip"
                       data-bs-placement="right"
                       title="매일(또는 음봉일) 매수할 금액을 입력합니다."></i>
                </label>
                <input type="text" class="form-control" id="buildupAmount" name="buildupAmount" placeholder="하루에 적립할 금액을 입력하세요">
            </div>

            <div class="d-grid">
                <button type="submit" class="btn btn-primary btn-lg">시뮬레이션 시작</button>
            </div>
        </form>
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
