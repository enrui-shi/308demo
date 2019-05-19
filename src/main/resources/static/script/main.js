// get slide bar value
$(document).ready(function () {
    $('#phase1').prop('disabled', false);
    $('#phase2').prop('disabled', true);
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

        fakeLog("Current preference:");
        fakeLog("<&nbsp>Weight of efficiency:" );
        console.log(preference_data.majorityMinorityDistrictNumber, " ", preference_data.ethnicGroupBound);
        $.ajax({
            type: 'post',
            url: '/home/main/startPhaseOne',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(preference_data),
            dataType: "json",
            success: function (data) {
                /* response from controller */
                console.log("color color : "+data.colors);

                if(map.hasLayer(districtLayer)){
                    map.removeLayer(districtLayer);
                }
                console.log("mmmmmmm"+Object.keys(data.colors));
                console.log("qqqqqqq"+(Object.keys(data.colors)[0])/10000000);
                if(((Object.keys(data.colors)[0])/10000000) == 1) {
                    districtLayer = L.geoJSON(oh_data.FeatureCollection, {
                        onEachFeature: precinctOnEachFeature,
                        style: function (feature) {
                            for (var i = 0; i < Object.keys(data.colors).length; i++) {
                                if (feature.id == Object.keys(data.colors)[i]) {
                                    console.log("id is " + Object.keys(data.colors)[i]);
                                    console.log("layer id is " + feature.id);
                                    console.log("district color is " + data.colors[feature.id]);
                                    return {fillColor: data.colors[feature.id]};
                                }
                            }
                        }
                    }).addTo(map);
                } else if(((Object.keys(data.colors)[0])/10000000) == 2) {
                    districtLayer = L.geoJSON(NY_precinctsData.FeatureCollection, {
                        onEachFeature: precinctOnEachFeature,
                        style: function (feature) {
                            for (var i = 0; i < Object.keys(data.colors).length; i++) {
                                if (feature.id == Object.keys(data.colors)[i]) {
                                    console.log("id is " + Object.keys(data.colors)[i]);
                                    console.log("layer id is " + feature.id);
                                    console.log("district color is " + data.colors[feature.id]);
                                    return {fillColor: data.colors[feature.id]};
                                }
                            }
                        }
                    }).addTo(map);

                } else {
                    districtLayer = L.geoJSON(NJ_precinctsData.FeatureCollection, {
                        onEachFeature: precinctOnEachFeature,
                        style: function (feature) {
                            for (var i = 0; i < Object.keys(data.colors).length; i++) {
                                if (feature.id == Object.keys(data.colors)[i]) {
                                    console.log("id is " + Object.keys(data.colors)[i]);
                                    console.log("layer id is " + feature.id);
                                    console.log("district color is " + data.colors[feature.id]);
                                    return {fillColor: data.colors[feature.id]};
                                }
                            }
                        }
                    }).addTo(map);
                }
                // enable phase2 button to start simulating annealing
                $('#phase2').prop('disabled', false);
                $('#phase1').prop('disabled', true);

                // start phase2
                function ajaxPhase2() {
                    $.ajax({
                      type: 'get',
                      url: "/home/main/startPhaseTwo",
                      contentType: "application/json; charset=utf-8",
                      header: {"accept": "application/json"},
                      dataType: "json",
                      success:function(data) {
                          console.log(data);
                          if (data.status == 'end') {
                              // after phase two finish, enable playphase1
                              $('#phase1').prop('disabled', false);
                              $('#phase2').prop('disabled', true);
                          } else {
                              // continue send ajax call
                              ajaxPhase2();
                          }
                      }
                    });
                }
            }
        })
    })
});
