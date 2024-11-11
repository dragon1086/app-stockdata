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

function checkMobileDevice() {
    // 모바일 디바이스 체크
    var isMobile = /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent);
    if (isMobile) {
        if (!sessionStorage.getItem('mobileWarningShown')) {
            var result = confirm('이 웹사이트는 PC 환경에 최적화되어 있습니다.\n계속 진행하시겠습니까?');
            if (!result) {
                // 취소를 누르면 홈페이지로 리다이렉트
                window.location.href = "/";
            }
            sessionStorage.setItem('mobileWarningShown', 'true');
        }
    }
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

    // 모바일 체크
    checkMobileDevice();

    // 화면 크기 변경 시 모바일 체크 (debounce 적용)
    $(window).on('resize', _.debounce(function() {
        checkMobileDevice();
    }, 250));
});