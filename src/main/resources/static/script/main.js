// get slide bar value
function slideForm() {
    $(document).ready(function () {
        var preference_form = $('$preference');
        preference_form.submit(function (e){
            var preference_data = {efficiencyGapWeight: $('#efficiencyGap').val(),
                compactnessWeight: $('#compactness').val(),
                partisanFairnessWeight: $('#partisanFairness').val(),
                equalPopulationWeight: $('#equalPopulation').val(),
                naturalConstraintWeight: $('#naturalConstraint').val(), numberOfDistrict: $('#numOfDistrict').val(),
                majorityMinorityDistrictNumber: $('#majority-minority').val(),
                africanAmericanBound: { upperBound: $('#maxAA').val(), lowerBound: $('#minAA').val() },
                asianBound: { upperBound: $('#maxAsian').val(), lowerBound: $('#minAsian').val() },
                latinoBound:{ upperBound: $('#maxLatino').val(), lowerBound: $('#minLatino').val() }
                };
            e.preventDefault();
            console.log(preference_data.majorityMinorityDistrictNumber, " ", preference_data.africanAmericanBound);
            $.ajax({
                type: 'post',
                url: '/home/main',
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(preference_data),
                dataType: "json",
                success: function (data) {
                    console.log(data);
                    window.location.replace("/home/main");

                }
            })
        })
    });
}
