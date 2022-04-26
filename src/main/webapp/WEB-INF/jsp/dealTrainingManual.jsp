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
    <meta name="description" content="주식 일봉매매 시뮬레이션">
    <meta name="author" content="펭수르">
    <meta name="generator" content="stock-buildup 0.0.1">
    <title>주식 일봉매매 시뮬레이션 메뉴얼</title>
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
        p { font-size: 15px; }
        img { max-width: 100%; display: block; margin: 0px 0px 500px 0px; }
    </style>
    <!-- Custom styles for this template -->
    <link href="/resources/css/buildup.css" rel="stylesheet">
    <link href="/resources/css/blogpost.css" rel="stylesheet">
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
            <h1 class="display-3 fw-bold">상추 일봉매매 시뮬레이션</h1>
            <h1 class="display-3 fw-bold">사용메뉴얼</h1>
            <h3 class="pb-4 mb-4 fst-italic border-bottom">
                수익이 파란색이라도 빨간색으로 만들어나갈 수 있는 실력을 키워보아요
            </h3>
        </div>

        <article class="blog-post">
            <div class="text-center">
                <h2><strong>만든 배경</strong></h2>
                <h4 class="blog-post-meta">2022년 1월 23일 by 주식시뮬레이션 훈련하는 개발자</h4>
            </div>
            <p>아무것도 모르던 불과 1년 전. 저는 가치투자를 한답시고, 나름 저평가된 주식을 찾아서 바로 다음날 몰빵을 했습니다.</p>
            <p>2021년에 시장이 좋을 때에는, 성공률이 꽤나 높았습니다. 하지만 하반기부터 하락이 시작된 이후부터는 물려있을 수 밖에 없더군요.</p>
            <p>그리고 남들에게 주식을 배우기 시작하며, 깨달았습니다. 아, 우리나라 주식은 하락이 일반적인 경우구나..!! 그럼에도 수익을 내야 하는구나.</p>
            <p>그래서 지금은 매일 종가매수를 통해 시드를 키워나가고, 나머지 금액으로 일반적인 매매를 하고 있습니다.</p>
            <p>일반 매매를 하면서 과거의 차트를 켜놓고 상상으로 시뮬레이션을 해봤는데, 훈련 효과가 적더군요.</p>
            <p>자연스레 "매일매일 매수/매도/건너뛰기를 판단하면서 실적을 측정하면서 훈련해보고 싶다"라는 욕심이 생겼습니다.</p>
            <p>그래서 주식 일봉매매 시뮬레이션을 만들었습니다.</p>
            <p></p>
            <p>이 메뉴얼은 일반적인 매매의 훈련을 위한 일봉매매 시뮬레이션 메뉴얼입니다.</p>
            <p>삼성전자가 좋아보여서 내가 할당한 금액 중 80%를 샀는데 물려있는 상황이라면, 오를 것이라 믿고 묻어두는 것도 좋습니다.</p>
            <p>하지만 능동적으로 비중을 조절하고, 리밸런싱 하면서 수익을 극대화하고 싶다면 매매훈련을 통해 기본기를 쌓는 것이 좋습니다.</p>
            <p>일봉 매매 시뮬레이션은 일봉차트 기준으로 매수/매도 훈련을 도와주어 매매 기본기 훈련에 도움이 될 것입니다.</p>
            <p>아쉽게도 분봉차트는 지원하지 않아서, 여러분이 분봉으로 대응했다고 가정하고 당일 평균 매도/매수 단가를 입력해야 합니다.</p>
            <p>그럼에도 꾸준히 일봉차트로 시뮬레이션 훈련을 하다보면, 실전매매에서도 효과를 발휘할 것이라 믿습니다.</p>
            <hr>
            <div class="text-center">
                <h2><strong>기능 설명</strong></h2>
                <h4 class="blog-post-meta">2022년 1월 23일 by 주식시뮬레이션 훈련하는 개발자</h4>
            </div>
            <p>우선 메인화면에서 글자들이 적혀있는걸 보실 수 있습니다. 시뮬레이션의 목표와 시작 방식을 읽어봐주세요.</p>
            <p>그리고 시뮬레이션 할 종목명, 계좌에 배정할 금액, 초기 매수 비중을 입력하고 "전송"을 클릭합니다. (모든 값은 필수로 입력해주세요)</p>
            <p>시작비중을 0으로 입력하면 처음부터 시작할 수 있고, 그 이상을 입력하면 평균매입단가가 자동으로 설정되어 시작합니다.</p>
            <img src="resources/images/dealTrainingMain.png" style="width:60%;margin: auto" loading="lazy">
            <p>처음으로 마주 할 결과 화면입니다. 랜덤으로 시작한 날짜 당시의 상황이 보입니다.</p>
            <p>매수만 시작비중만큼 했고, 매도는 없는 상황입니다.</p>
            <p>이번 예시에서는 시작 시 평가손익이 랜덤으로 -12.65%로 설정되었고, 평균단가는 4,528원이네요.</p>
            <img src="resources/images/dealTrainingInitial1.png" style="width:60%;margin: auto" loading="lazy">
            <p>그 아래에는 시작 시 랜덤하게 지정된 시작날짜(2009.07.01)이 보이고, 추가 매수/매도를 위한 입력창이 보이네요.</p>
            <p>만약 차트를 보시고 이 날 매수/매도를 해보고 싶으시면 값을 입력하시고, 그냥 넘어가고 싶으시면 입력없이 "다음" 버튼을 누르시면 다음개장일로 넘어갑니다.</p>
            <p>이 때 비중은 최초 계좌에 설정한 금액 대비 비중이고, 이 예시에서는 1천만원입니다. 가격은 매수/매도 하고 싶으신 단가입니다.</p>
            <p></p>
            <p>효과적인 훈련을 위해 다음 일봉 예상치를 기입할 수 있게 해놨습니다.</p>
            <p>다음날 예상되는 분봉흐름을 머릿속에 그려보시고, 시가/종가/저가/고가를 입력해보세요.(결과에는 영향 없습니다.)</p>
            <img src="resources/images/dealTrainingInitial2.png" style="width:60%;margin: auto" loading="lazy">
            <p>그 아래에는 실제 주식 일봉 캔들차트가 보입니다. 캔들차트와 평균단가 그래프를 겹쳐놨습니다.</p>
            <p>만약 평균단가와 일봉차트의 간격이 커서 일봉차트가 좁게 보인다면, 그래프 아래 범례에서 평균단가를 클릭해서 평균단가를 없앰으로써 일봉차트를 크게 볼 수 있습니다.</p>
            <p>일봉차트의 보기 범위를 1m(1달), 3m(3달), 6m(6달), YTD(당해년도), 1y(1년), All(전체)로 볼 수 있습니다.</p>
            <p>그 외에도 거래량 그래프, 비중변화 꺾은선 그래프, 매수/매도 금액이력 그래프도 보실 수 있습니다.</p>
            <p>가장 아래 미니맵을 통해 차트 보기 범위를 수동으로 설정할 수 있습니다. 좌우로 이동하거나, 넓히고 줄일 수 있습니다.</p>
            <img src="resources/images/dealTrainingInitial3.png" style="width:60%;margin: auto" loading="lazy">
            <p>차트 범위를 최대(All)로 넓히면 과거 3년치 일봉까지 볼 수 있습니다.</p>
            <img src="resources/images/dealTrainingInitial5.png" style="width:60%;margin: auto" loading="lazy">
            <p>최초입력 요청값, 매수/매도 이력, 날짜별 전체 이력을 통해 이력을 살펴보실 수 있습니다.</p>
            <p>특히 날짜별 전체 이력에는 추가 매수/매도 금액과 실현손익 이력이 있으므로, 이를 드래그해서 엑셀에 복사해서 분석에 활용하실 수 있습니다.</p>
            <img src="resources/images/dealTrainingInitial4.png" style="width:60%;margin: auto" loading="lazy">
            <p>자, 이제 본격적인 매매시뮬레이션을 해보겠습니다.</p>
            <p>저는 일봉차트를 이리저리 보면서 며칠동안 아무것도 하지 않고 "다음"으로 넘겼습니다.</p>
            <p>이후 2009.07.06에 흐름이 안정되었다고 판단해서 2% 추가매수를 했습니다.</p>
            <p>매수 단가를 결정할 때에는, 제가 실제로 이 날엔 흐름을 지켜보다가 종가에 매수했다고 가정했습니다. 그래서 종가인 4,050원으로 결정했습니다.</p>
            <p>그래서 매수비중은 2%, 매수단가는 4,050으로 입력하고 "다음"을 눌렀습니다.</p>
            <img src="resources/images/dealTraining1.png" style="width:60%;margin: auto" loading="lazy">
            <p>그러면 새로운 계산결과가 나오고, 그래프를 보면 매수로 인해 변경된 그래프를 보실 수 있습니다.</p>
            <p>여기서는 평균단가가 4,528원에서 4,503원으로 내려갔고, 비중은 40%에서 41.96%로 올라갔습니다. 매수금액은 198,450원이네요.</p>
            <img src="resources/images/dealTraining2.png" style="width:60%;margin: auto" loading="lazy">
            <p>계속 진행하다보니, 2009.7.21에 리밸런싱 기회가 와주네요! 매수와 매도를 섞으며 대응했습니다.</p>
            <p>장 초반에 거래량이 터지면서 돌파하는 모습을 보여서 평균 3,900원에 매수했다고 가정하고,</p>
            <p>고점이라 판단한 부분에서 4,250원에 2% 매도했다고 가정했습니다.</p>
            <p>비중은 45.93%가 되었고, 매수금액은 397,800원, 매도금액은 199,750원입니다</p>
            <img src="resources/images/dealTraining3.png" style="width:60%;margin: auto" loading="lazy">
            <p>가장 위쪽에 보면 리밸런싱 매도로 인해 실현손실이 발생한 것을 보실 수 있습니다. 이를 체크해가면서 향후 대응하면 됩니다.</p>
            <img src="resources/images/dealTraining4.png" style="width:60%;margin: auto" loading="lazy">
            <p>이어서 매매를 하다가, 만족할만한 수익이 나면 전량매도를 하면 됩니다. 저도 전량매도를 하고 싶어서 캡쳐화면처럼 비중 100%를 입력했습니다.</p>
            <img src="resources/images/dealTraining5.png" style="width:60%;margin: auto" loading="lazy">
            <p>전량매도 하면 결과데이터에서 총 매입수량과 총 매도수량이 같아집니다. 와~ 이번 예제에서는 5.96%의 수익을 달성했군요.</p>
            <img src="resources/images/dealTraining6.png" style="width:60%;margin: auto" loading="lazy">
            <p>예제의 매매 기간은 2009.07.01부터 2009.09.03까지 약 2달여간 진행했습니다.</p>
            <p>중간에 비중과 평균단가를 조절하기 위해 리밸런싱을 섞었고, 마지막즈음엔 분할매도를 통해 마무리 했습니다.</p>
            <p>그래프 하단에 매수/매도금액 그래프가 있는데, 이것을 통해 어느정도 강도로 매수/매도를 진행해왔는지 가늠해볼 수 있습니다.</p>
            <img src="resources/images/dealTraining7.png" style="width:60%;margin: auto" loading="lazy">
            <p>스크롤을 가장 아래로 내리면, 매매기간동안 내역이 그대로 나옵니다. 표를 긁어서 활용하셔도 됩니다.</p>
            <img src="resources/images/dealTraining8.png" style="width:60%;margin: auto" loading="lazy">
            <hr>
        </article>
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
