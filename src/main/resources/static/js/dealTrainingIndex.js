$(function(){
    document.cookie = "SameSite=None; Secure";
    initTooltips();
    initAutocomplete();
});

function companyNameOnOff(){
    var companyNameCheckbox = document.getElementById("companyNameCheck");
    document.getElementById("companyNameInput").disabled = !companyNameCheckbox.checked;
}

function startDateOnOff(){
    var startDateCheckbox = document.getElementById("startDateCheck");
    document.getElementById("startDate").disabled = !startDateCheckbox.checked;
}

function valuationPercentOnOff(){
    var valuationPercentCheckbox = document.getElementById("valuationPercentCheck");
    document.getElementById("valuationPercent").disabled = !valuationPercentCheckbox.checked;
}

function openDealHistoryTab() {
    window.open('/deal-calculate-histories', '_blank');
}

function initTooltips() {
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
    tooltipTriggerList.forEach(function (tooltipTriggerEl) {
        new bootstrap.Tooltip(tooltipTriggerEl)
    })
}

function initAutocomplete() {
    $("#companyNameInput").autocomplete({
        source : function( request, response ) {
            $.ajax({
                type: 'post',
                url: "/company",
                dataType: "json",
                data: {"keyword":$("#companyNameInput").val()},
                success: function(data) {
                    response(
                        $.map(data, function(item) {
                            return {
                                label: item,
                                value: item,
                            }
                        })
                    );
                }
            });
        },
        focus : function(event, ui) {
            return false;
        },
        minLength: 1,
        autoFocus: true,
        classes: {
            "ui-autocomplete": "highlight"
        },
        delay: 500,
        select: function(event, ui) {
            console.log("Selected: " + ui.item.value);
        }
    });
}