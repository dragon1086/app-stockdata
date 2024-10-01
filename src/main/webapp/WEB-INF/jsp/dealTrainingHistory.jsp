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
    <script type="text/javascript" src="/resources/js/bootstrap.js"></script>

    <link rel="stylesheet" href="/resources/css/bootstrap.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <link rel="stylesheet" href="//code.jquery.com/ui/1.13.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/ui/1.13.1/jquery-ui.js"></script>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <style>
        body { font-family: Arial, sans-serif; font-size: 14px; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
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
    <div class="container">
        <h2 class="mt-4 mb-4">시뮬레이션 이력</h2>
        <div id="historiesContainer"></div>
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
