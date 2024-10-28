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