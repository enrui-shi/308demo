// get slide bar value
$(document).ready(function () {
    var preference_form = $('#preference');
    preference_form.submit(function (e){
        console.log("start to send preference date")
        var preference_data = {};
        preference_data[efficiencyGapWeight] = $('#efficiencyGap').val();
        preference_data[compactnessWeight] = $('#compactness').val();
        preference_data[partisanFairnessWeight] = $('#partisanFairness').val();
        preference_data[equalPopulationWeight] = $('#equalPopulation').val();
        preference_data[naturalConstraintWeight] = $('#naturalConstraint').val();
        preference_data[numberOfDistrict] = $('#numOfDistrict').val();
        preference_data[majorityMinorityDistrictNumber] = $('#majority-minority').val();
        var mm_data = {};
        mm_data[AFRIAN_AMERICAN] = $('#mmAA').val();
        mm_data[ASIAN_PACIFIC] = $('#mmAsian').val();
        mm_data[LATINO] = $('#mmLatino').val();

        preference_data[ethnicGroupNumber] = mm_data;

        var enthnic_data = {};

        enthnic_data[AFRIAN_AMERICAN] = { upperBound: $('#maxAA').val()/100, lowerBound: $('#minAA').val()/100 };
        enthnic_data[ASIAN_PACIFIC] = { upperBound: $('#maxAsian').val()/100, lowerBound: $('#minAsian').val()/100 };
        enthnic_data[LATINO] = { upperBound: $('#maxLatino').val()/100, lowerBound: $('#minLatino').val()/100 };

        preference_data[ethnicGroupBound] = enthnic_data ;


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
    })
    $('#menubtn').prop('disabled', true);
});

