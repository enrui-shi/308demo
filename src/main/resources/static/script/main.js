// get slide bar value
$(document).ready(function () {
    var preference_form = $('#preference');
    preference_form.submit(function (e){
        console.log("start to send preference date")

        var mm_data = {AFRIAN_AMERICAN:$('#mmAA').val(), ASIAN_PACIFIC:$('#mmAsian').val(),LATINO:$('#mmLatino').val()};

        var enthnic_data = {AFRIAN_AMERICAN:{ upperBound: $('#maxAA').val()/100, lowerBound: $('#minAA').val()/100 },
                            ASIAN_PACIFIC:{ upperBound: $('#maxAsian').val()/100, lowerBound: $('#minAsian').val()/100 },
                            LATINO: { upperBound: $('#maxLatino').val()/100, lowerBound: $('#minLatino').val()/100 }};

        var preference_data = {efficiencyGapWeight: $('#efficiencyGap').val(),
            compactnessWeight: $('#compactness').val(), partisanFairnessWeight: $('#partisanFairness').val(),
            equalPopulationWeight: $('#equalPopulation').val(), naturalConstraintWeight:$('#naturalConstraint').val(),
            numberOfDistrict:$('#numOfDistrict').val(), majorityMinorityDistrictNumber:$('#majority-minority').val(),
            ethnicGroupNumber: mm_data, ethnicGroupBound: enthnic_data};

        e.preventDefault();

        console.log(preference_data.majorityMinorityDistrictNumber, " ", preference_data.ethnicGroupBound);
        $.ajax({
            type: 'post',
            url: '/home/main/startPhaseOne',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(preference_data),
            dataType: "json",
            success: function (data) {
                /* response from controller */
                console.log(data);
            }
        })
        $.ajax({
            type: 'get',
            url: "/home/main/startPhaseTwo",
            contentType: "application/json; charset=utf-8",
            header: {"accept": "application/json"},
            success: function (data) {
                /* response from controller */
                console.log(data);
            }
        })
    })
    $('#menubtn').prop('disabled', true);
});

