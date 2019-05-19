// get slide bar value
$(document).ready(function () {
    $('#phase1').prop('disabled', false);
    $('#phase2').prop('disabled', true);
    document.getElementById('inputerror').style.display="none";
    var preference_form = $('#preference');
    preference_form.submit(function (e){
        console.log("start to send preference date")
        // check form data
        if($('#maxAA').val() < $('#minAA').val()){
            document.getElementById('inputerror').style.display="block";
            document.getElementById('inputerror').innerHTML="American American min population > max!";
        }
        else if(($('#mmAA').val()+$('#mmAsian').val()+$('#mmLatino').val()) > $('#numOfDistrict').val()) {
            document.getElementById('inputerror').style.display="block";
            document.getElementById('inputerror').innerHTML="Sum of majority minority districts beyond total number!";
        } else if($('#maxAsian').val() < $('#minAsian').val()){
            document.getElementById('inputerror').style.display="block";
            document.getElementById('inputerror').innerHTML="Asian min population > max!";
        } else if($('#maxLatino').val() < $('#minLatino').val()){
            document.getElementById('inputerror').style.display="block";
            document.getElementById('inputerror').innerHTML="Latino min population > max!";
        } else {

            var mm_data = {
                AFRIAN_AMERICAN: $('#mmAA').val(),
                ASIAN_PACIFIC: $('#mmAsian').val(),
                LATINO: $('#mmLatino').val()
            };

            var enthnic_data = {
                AFRIAN_AMERICAN: {upperBound: $('#maxAA').val() / 100, lowerBound: $('#minAA').val() / 100},
                ASIAN_PACIFIC: {upperBound: $('#maxAsian').val() / 100, lowerBound: $('#minAsian').val() / 100},
                LATINO: {upperBound: $('#maxLatino').val() / 100, lowerBound: $('#minLatino').val() / 100}
            };

            var preference_data = {
                efficiencyGapWeight: $('#efficiencyGap').val(),
                compactnessWeight: $('#compactness').val(),
                partisanFairnessWeight: $('#partisanFairness').val(),
                equalPopulationWeight: $('#equalPopulation').val(),
                naturalConstraintWeight: $('#naturalConstraint').val(),
                numberOfDistrict: $('#numOfDistrict').val(),
                ethnicGroupNumber: mm_data,
                ethnicGroupBound: enthnic_data
            };

            e.preventDefault();

            fakeLog("Current preference:");
            fakeLog("&nbspWeight of efficiency:" + preference_data.efficiencyGapWeight);
            fakeLog("&nbspWeight of compactness:" + preference_data.compactnessWeight);
            fakeLog("&nbspWeight of partisan fairness:" + preference_data.partisanFairnessWeight);
            fakeLog("&nbspWeight of equal population:" + preference_data.equalPopulationWeight);
            fakeLog("&nbspWeight of natural constraint:" + preference_data.naturalConstraintWeight);
            fakeLog("&nbspNumber of district:" + preference_data.numberOfDistrict);
            fakeLog("&nbspAFRIAN_AMERICAN");
            fakeLog("&nbsp&nbspminority district number:" + $('#mmAA').val());
            fakeLog("&nbsp&nbspmin population threshold:" + $('#minAA').val() / 100);
            fakeLog("&nbsp&nbspmax population threshold:" + $('#maxAA').val() / 100);
            fakeLog("&nbspASIAN_PACIFIC bound of minority:");
            fakeLog("&nbsp&nbspminority district number:" + $('#mmAsian').val());
            fakeLog("&nbsp&nbspmin population threshold:" + $('#minAsian').val() / 100);
            fakeLog("&nbsp&nbspmax population threshold::" + $('#maxAsian').val() / 100);
            fakeLog("&nbspLatino bound of minority:");
            fakeLog("&nbsp&nbspminority district number:" + $('#mmLatino').val());
            fakeLog("&nbsp&nbspmin population threshold:" + $('#minLatino').val() / 100);
            fakeLog("&nbsp&nbspmax population threshold:" + $('#maxLatino').val() / 100);

            $.ajax({
                type: 'post',
                url: '/home/main/startPhaseOne',
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(preference_data),
                dataType: "json",
                success: function (data) {
                    // response from controller
                    console.log("color color : " + data.colors);

                    if (map.hasLayer(districtLayer)) {
                        map.removeLayer(districtLayer);
                    }

                    if ((data.colors.keys[0] / 10000000) == 1) {
                        districtLayer = L.geoJSON(OH_precinctsData.FeatureCollection, {
                            onEachFeature: precinctOnEachFeature,
                            style: function (feature) {
                                for (var i = 0; i < data.colors.length; i++) {
                                    if (feature.id == data.colors.keys[i]) {
                                        console.log("id is " + data.colors.keys[i]);
                                        console.log("layer id is " + feature.id);
                                        console.log("district color is " + data.colors[feature.id]);
                                        return {fillColor: data.colors[feature.id]};
                                    }
                                }
                            }
                        }).addTo(map);
                    } else if ((data.colors.keys[0] / 10000000) == 2) {
                        districtLayer = L.geoJSON(NY_precinctsData.FeatureCollection, {
                            onEachFeature: precinctOnEachFeature,
                            style: function (feature) {
                                for (var i = 0; i < data.colors.length; i++) {
                                    if (feature.id == data.colors.keys[i]) {
                                        console.log("id is " + data.colors.keys[i]);
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
                                for (var i = 0; i < data.colors.length; i++) {
                                    if (feature.id == data.colors.keys[i]) {
                                        console.log("id is " + data.colors.keys[i]);
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
                            success: function (data) {
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
        }
    })
});
