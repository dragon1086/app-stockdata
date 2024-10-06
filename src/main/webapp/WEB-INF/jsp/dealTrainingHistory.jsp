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
    <meta name="description" content="시뮬레이션 이어하기">
    <meta name="author" content="펭수르">
    <meta name="generator" content="stock-buildup 0.0.1">
    <meta name="google-site-verification" content="8TTVWhEDuj8mMlx0fjRf-TGRRHi4qA7HnI_4cCEbFXQ" />
    <title>시뮬레이션 이어하기</title>
    <!--  부트스트랩 js 사용 -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <link rel="stylesheet" href="//code.jquery.com/ui/1.13.1/themes/base/jquery-ui.css">
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

        .main-container {
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 0 30px rgba(0,0,0,0.1);
            padding: 3rem;
            margin-top: 3rem;
        }

        table {
            width: 100%;
            margin-bottom: 1rem;
            color: #212529;
            border-collapse: collapse;
        }

        th, td {
            padding: 0.75rem;
            vertical-align: top;
            border-top: 1px solid #dee2e6;
        }

        thead th {
            vertical-align: bottom;
            border-bottom: 2px solid #dee2e6;
            background-color: #f8f9fa;
        }

        .btn-primary {
            background-color: #3498db;
            border-color: #3498db;
        }

        .btn-primary:hover {
            background-color: #2980b9;
            border-color: #2980b9;
        }

        footer {
            background-color: #2c3e50;
            color: #ecf0f1;
            padding: 1rem 0;
            margin-top: 4rem;
        }
    </style>
    <!-- Custom styles for this template -->
    <link href="/resources/css/buildup.css" rel="stylesheet">
    <script>
        $(function(){
            document.cookie = "SameSite=None; Secure";
            renderDealTrainingHistories();
        });

        function renderDealTrainingHistories() {
            var isError = ${isError};
            var errorMessage = "${errorMessage}";
            var redirectUrl = "${redirectUrl}";
            var dealTrainingHistories = ${dealTrainingSourceDTOs};

            if (isError) {
                alert(errorMessage);
                if (redirectUrl) {
                    window.location.href = redirectUrl;
                }
                return;
            }

            var tableHtml = '<table class="table table-bordered">' +
                '<thead><tr>' +
                '<th>기업 이름</th>' +
                '<th>배분한 금액</th>' +
                '<th>현재비중</th>' +
                '<th>시작일</th>' +
                '<th>종료일</th>' +
                '<th>최초시작 비중</th>' +
                '<th>최초시작 평가손익(%)</th>' +
                '<th>작업</th>' +
                '</tr></thead><tbody>';

            dealTrainingHistories.forEach(function(history) {
                var historyJson = JSON.stringify(history).replace(/'/g, "&#39;").replace(/"/g, "&quot;");
                tableHtml += '<tr>' +
                    '<td>' + history.companyName + '</td>' +
                    '<td>' + history.slotAmount + '</td>' +
                    '<td>' + history.portion + '</td>' +
                    '<td>' + history.startDate + '</td>' +
                    '<td>' + history.endDate + '</td>' +
                    '<td>' + history.initialPortion + '</td>' +
                    '<td>' + history.valuationPercent + '</td>' +
                    '<td><button class="btn btn-primary" onclick="continueTraining(\'' + historyJson + '\')">이어하기</button></td>' +
                    '</tr>';
            });

            tableHtml += '</tbody></table>';
            $('#historiesContainer').html(tableHtml);
        }

        function continueTraining(historyJson) {
            var history = JSON.parse(historyJson);
            var form = document.createElement('form');
            form.method = 'POST';
            form.action = '/previous-deal-calculate';

            var inputs = {
                'id': history.id,
                'companyName': history.companyName,
                'startDate': history.startDate,
                'endDate': history.endDate,
                'slotAmount': history.slotAmount,
                'portion': history.portion,
                'initialPortion': history.initialPortion
            };

            for (var key in inputs) {
                var input = document.createElement('input');
                input.type = 'hidden';
                input.name = key;
                input.value = inputs[key];
                form.appendChild(input);
            }

            if (history.dealModifications) {
                history.dealModifications.forEach(function(modification, index) {
                    for (var key in modification) {
                        var input = document.createElement('input');
                        input.type = 'hidden';
                        input.name = 'dealModifications[' + index + '].' + key;
                        input.value = modification[key];
                        form.appendChild(input);
                    }
                });
            }

            document.body.appendChild(form);
            form.submit();
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
                        <% } %>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container main-container">
        <h2 class="mb-4">시뮬레이션 이력</h2>
        <div id="historiesContainer"></div>
    </div>

    <!-- Footer -->
    <footer class="text-center text-lg-start">
        <div class="container">
            <div class="text-center p-4">
                개발자(펭수르) 개별 문의처: <a class="text-reset fw-bold" href="mailto:dragon1086@naver.com">dragon1086@naver.com</a>
                <p class="mb-0">주식 데이터 구매 문의처: <a class="text-reset fw-bold" href="https://kmong.com/gig/245871" target="_blank">https://kmong.com/gig/245871</a></p>
            </div>
        </div>
    </footer>
    <!-- Footer -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
