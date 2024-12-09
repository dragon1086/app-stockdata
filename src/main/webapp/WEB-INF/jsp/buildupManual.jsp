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
    <meta name="generator" content="stock-buildup 0.0.1">
    <title>자동 시뮬레이션기 메뉴얼</title>
    <!--  부트스트랩 js 사용 -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script type="text/javascript" src="/resources/js/bootstrap.js"></script>

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
        p { font-size: 15px; }
        img { max-width: 100%; display: block; margin: 0px 0px 500px 0px; }
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
    </style>
    <!-- Custom styles for this template -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
    <script>
        $(function(){
            document.cookie = "SameSite=None; Secure";
        });
    </script>
</head>
<body>
    <div class="container">
        <header class="d-flex flex-wrap justify-content-center py-3 mb-4 border-bottom">
            <ul class="nav nav-pills">
                <li class="nav-item"><a href="/" class="nav-link active" aria-current="page">초기화면</a></li>
            </ul>
        </header>
    </div>

    <div class="px-4 py-5 my-5">
        <div class="text-center">
            <h1 class="display-3 fw-bold">자동 시뮬레이션</h1>
            <h1 class="display-3 fw-bold">사용메뉴얼</h1>
            <h3 class="pb-4 mb-4 fst-italic border-bottom">
                계좌의 뒤를 지켜줄 든든한 시드를 성장시켜보아요
            </h3>
        </div>

        <article class="blog-post">
            <div class="text-center">
                <h2><strong>만든 배경</strong></h2>
                <h4 class="blog-post-meta">2022년 1월 13일 by 펭수르</h4>
            </div>
            <p>아무것도 모르던 불과 1년 전. 저는 가치투자를 한답시고, 나름 저평가된 주식을 찾아서 바로 다음날 몰빵을 했습니다.</p>
            <p>2021년에 시장이 좋을 때에는, 성공률이 꽤나 높았습니다. 하지만 하반기부터 하락이 시작된 이후부터는 물려있을 수 밖에 없더군요.</p>
            <p>그리고 주월클에서 주식을 배우기 시작하며, 깨달았습니다. 아, 우리나라 주식은 하락이 일반적인 경우구나..!! 그럼에도 수익을 내야 하는구나.</p>
            <p>주월클 회원분들도 아시겠지만, 제가 배운 계좌의 중심을 만드는 방법은 내가 목표한 주식을 매일매일 매수를 하면서 적금처럼 모아가는 것입니다.</p>
            <p>우리나라 주식은 박스를 그리며 싸이클을 탑니다. 이 상승과 하락을 이용해서 계속 모아가다가 수익을 내고, 다음 종목 정해서 또 시작하는 것이 좋다고 생각했습니다.</p>
            <p>그러면 어떤 종목이 박스가 길고 짧을까? 이 종목은 어느정도 모아가면 수익을 낼 수 있을까? 만약 내가 삼성전자를 10년간 모아갔다면 얼마나 수익이 날까?</p>
            <p>등등 다양한 궁금증이 생깁니다. 이 궁금증을 충족시키기 위해 '자동 시뮬레이션기'를 만들었습니다.</p>
            <hr>
            <div class="text-center">
                <h2><strong>기능 설명</strong></h2>
                <h4 class="blog-post-meta">2022년 1월 20일 by 펭수르</h4>
            </div>
            <p>우선 메인화면에서 "자동 시뮬레이션 하러 가기"를 클릭합니다.</p>
            <img src="resources/images/buildUpMain2.png" style="width:60%;margin: auto" loading="lazy">
            <p>이후 종가매수 할 시뮬레이션 종목명, 시작날짜, 종료 및 전량매도날짜, 매일 매수 할 금액을 입력하고 "전송"을 클릭합니다.</p>
            <img src="resources/images/buildUpMain.png" style="width:60%;margin: auto" loading="lazy">
            <p>처음으로 마주 할 결과 화면입니다. 매일 종가로 매수하다가 마지막 날 전량매도 시 결과데이터가 보입니다.</p>
            <p>그리고 간단한 꺾은선 그래프인 종가,평균단가 그래프가 보입니다.</p>
            <img src="resources/images/buildUpResult1.png" style="width:60%;margin: auto" loading="lazy">
            <p>그 아래에는 실제 주식 일봉 캔들차트가 보입니다. 캔들차트와 평균단가 그래프를 겹쳐놨습니다.</p>
            <p>만약 평균단가와 일봉차트의 간격이 커서 일봉차트가 좁게 보인다면, 그래프 아래 범례에서 평균단가를 클릭해서 평균단가를 없앰으로써 일봉차트를 크게 볼 수 있습니다.</p>
            <p>일봉차트의 보기 범위를 1m(1달), 3m(3달), 6m(6달), YTD(당해년도), 1y(1년), All(전체)로 볼 수 있습니다</p>
            <img src="resources/images/buildUpResult2.png" style="width:60%;margin: auto" loading="lazy">
            <p>일봉차트와 같은 범위에서 거래량차트도 보실 수 있습니다.</p>
            <p>가장 아래 미니맵을 통해 차트 보기 범위를 수동으로 설정할 수 있습니다. 좌우로 이동하거나, 넓히고 줄일 수 있습니다.</p>
            <img src="resources/images/buildUpResult3.png" style="width:60%;margin: auto" loading="lazy">
            <p>만약 검색하신 기간 내에서 추가로 매수/매도를 하면 결과가 어떻게 될 지 시뮬레이션을 해보고 싶으시면, 아래 기능을 이용하시면 됩니다.</p>
            <p>아래 이미지 참고하셔서 한번 또는 여러번의 매매를 입력해서 "수정 전송"을 하시면, 결과가 재계산되어 다시 나옵니다.</p>
            <img src="resources/images/buildUpResult4.png" style="width:60%;margin: auto" loading="lazy">
            <p>수정 전송 이후 변경된 결과는 아래 이미지들처럼 나타납니다.</p>
            <img src="resources/images/buildUpResult6.png" style="width:60%;margin: auto" loading="lazy">
            <img src="resources/images/buildUpResult7.png" style="width:60%;margin: auto" loading="lazy">
            <img src="resources/images/buildUpResult8.png" style="width:60%;margin: auto" loading="lazy">
            <p>스크롤을 가장 아래로 내리시면, 최초 입력값과 일자별 매매이력 데이터를 보실 수 있습니다.</p>
            <p>이것을 드래그해서 엑셀로 붙여넣어서 활용하실 수 있습니다.</p>
            <img src="resources/images/buildUpResult5.png" style="width:60%;margin: auto" loading="lazy">
            <hr>
        </article>
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

    <script type="text/javascript" src="/resources/js/bootstrap.js"></script>
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
