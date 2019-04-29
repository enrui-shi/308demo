// get slide bar value
function slideForm() {
    $(document).ready(function () {
        var weight_form = $("#weight");
        weight_form.submit(function (e) {
            var weight_data = {
                efficiencyGapWeight: $('#efficiencyGap').val(),
                compactnessWeight: $('#compactness').val(),
                partisanFairnessWeight: $('#partisanFairness').val(),
                equalPopulationWeight: $('#equalPopulation').val(),
                naturalConstraintWeight: $('#naturalConstraint').val()
            };
            e.preventDefault();
            // check if the value
            console.log(weight_data.efficiencyGapWeight, " ", weight_data.compactnessWeight, " ",
                weight_data.partisanFairnessWeight, " ", weight_data.naturalConstraintWeight);

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
        });
        var preference_form = $('$slidebarNumber');
        preference_form.submit(function (e){
            var preference_data = {numberOfDistrict: $('#numOfDistrict').val(),
                majorityMinorityDistrictNumber: $('#majority-minority').val(),
                africanAmericanBound: { upperBound: $('#maxAA').val(), lowerBound: $('#minAA').val() },
                asianBound: { upperBound: $('#maxAsian').val(), lowerBound: $('#minAsian').val() },
                latinoBound:{ upperBound: $('#maxLatino').val(), lowerBound: $('#minLatino').val() }
                };
            e.preventDefault();
            console.log(preference_data.majorityMinorityDistrictNumber, " ", preference_data.maxThreshold);
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
