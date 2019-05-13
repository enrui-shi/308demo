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
                console.log("color color : "+data.colors);
                if(map.hasLayer(precinctLayer))
                    map.removeLayer(precinctLayer);
                precinctLayer = L.geoJSON(precinctsData.map, {
                    onEachFeature: precinctOnEachFeature,
                    style: function(feature) {
                        for(var i=0; i < data.colors.length; i++) {
                            if(feature.id == data.colors[i].precinctID){
                                console.log("id is "+ data.colors[i].precinctID);
                                console.log("layer id is "+feature.id);
                                console.log("district color is "+data.colors[i].district.color);
                                return {fillColor:data.colors[i].district.color};
                            }
                        }
                    }
                }).addTo(map);
            }
        })
        /*$.ajax({
            type: 'get',
            url: "/home/main/startPhaseTwo",
            contentType: "application/json; charset=utf-8",
            header: {"accept": "application/json"},
            success: function (data) {
                 response from controller
                console.log(data);
            }
        })*/
    })
    $('#menubtn').prop('disabled', true);
});

