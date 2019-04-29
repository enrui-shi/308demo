// get slide bar value
$(document).ready(function () {
    var preference_form = $("#preference");
    preference_form.submit(function (e) {
        var preference_data = {efficiencyGapWeight: $('#efficiencyGap').val(), compactnessWeight: $('#compactness').val(),
            partisanFairnessWeight:$('#partisanFairness').val(),equalPopulationWeight:$('#equalPopulation').val(),
            naturalConstraintWeight:$('#naturalConstraint').val(), numberOfDistrict:$('#numOfDistrict').val(),
            majorityMinorityDistrictNumber:$('#majority-minority').val(), minThreshold:$('#minThreshold').val(),
            maxThreshold:$('#maxThreshold').val()};
        e.preventDefault();
        console.log(preference_data.efficiencyGapWeight, " ", preference_data.password);
        $.ajax({
            type: 'post',
            url: '/user/main',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(preference_data),
            dataType: "json",
            success: function (data) {
                console.log(data);
                window.location.replace("/main");

            }
        })
    });
});

