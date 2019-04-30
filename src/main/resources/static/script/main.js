// get slide bar value
$(document).ready(function () {
    var preference_form = $('#preference');
    preference_form.submit(function (e){
        var preference_data = {};
        preference_data[efficiencyGapWeight] = $('#efficiencyGap').val();
        preference_data[compactnessWeight] = $('#compactness').val();
        preference_data[partisanFairnessWeight] = $('#partisanFairness').val();
        preference_data[equalPopulationWeight] = $('#equalPopulation').val();
        preference_data[naturalConstraintWeight] = $('#naturalConstraint').val();
        preference_data[numberOfDistrict] = $('#numOfDistrict').val();
        preference_data[majorityMinorityDistrictNumber] = $('#majority-minority').val();

        var enthnic_data = {};

        enthnic_data[AFRIAN_AMERICAN] = { upperBound: $('#maxAA').val(), lowerBound: $('#minAA').val() };
        ethnicGroupBound[ASIAN_PACIFIC] = { upperBound: $('#maxAsian').val(), lowerBound: $('#minAsian').val() };
        ethnicGroupBound[LATINO] = { upperBound: $('#maxLatino').val(), lowerBound: $('#minLatino').val() };

        preference_data[ethnicGroupBound] = enthnic_data ;


        e.preventDefault();

        console.log(preference_data.majorityMinorityDistrictNumber, " ", preference_data.ethnicGroupBound);
        $.ajax({
            type: 'post',
            url: '/home/main/startAlgorithm',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(preference_data),
            dataType: "json",
            success: function (data) {
                /* response from controller */
                console.log(data);
            }
        })
    })
});

