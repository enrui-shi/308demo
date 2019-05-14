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

                districtLayer = L.geoJSON(precinctsData.map, {
                    onEachFeature: precinctOnEachFeature,
                    style: function(feature) {
                        for(var i=0; i < data.colors.length; i++) {
                            for(var j=0; j<data.colors[i].district.precincts.length; j++)
                            if(feature.id == data.colors[i].district.precincts[j]){
                                console.log("id is "+ data.colors[i].district.precincts[j]);
                                console.log("layer id is "+feature.id);
                                console.log("district color is "+data.colors[i].district.d_color);
                                return {fillColor:data.colors[i].district.d_color};
                            }
                        }
                    }
                }).addTo(map);
                console.log("id is "+ data.colors[0].district.precincts[0].precinctID);
                console.log("district color is "+data.colors[0].district.d_color);
                console.log(hashmap[['OH', data.colors[0].district.precincts[0].precinctID]][0]);
                var latlngs = hashmap[['OH', data.colors[0].district.precincts[0].precinctID]][0];
                var polygon1 = L.polygon(latlngs, {fillColor: "#212529"}).addTo(map);
                /*for(var i=0; i < data.colors.length; i++) {
                    for (var j = 0; j < data.colors[i].district.precincts.length; j++) {

                    }
                }*/
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

