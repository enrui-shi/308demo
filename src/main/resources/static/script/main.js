// get slide bar value
$(document).ready(function () {
    $('#phase1').prop('disabled', false);
    $('#phase2').prop('disabled', true);
    var preference_form = $('#preference');
    preference_form.submit(function (e){
        document.getElementById('inputerror').style.display="none";
        console.log("start to send preference date")
        // check form data
        if($('#maxAA').val() < $('#minAA').val()){
            document.getElementById('inputerror').style.display="block";
            document.getElementById('inputerror').innerHTML="American American min population > max!";
            e.preventDefault();
        }
        else if(($('#mmAA').val()+$('#mmAsian').val()+$('#mmLatino').val()) > $('#numOfDistrict').val()) {
            document.getElementById('inputerror').style.display="block";
            document.getElementById('inputerror').innerHTML="Sum of majority minority districts beyond total number!";
            e.preventDefault();
        } else if($('#maxAsian').val() < $('#minAsian').val()){
            document.getElementById('inputerror').style.display="block";
            document.getElementById('inputerror').innerHTML="Asian min population > max!";
            e.preventDefault();
        } else if($('#maxLatino').val() < $('#minLatino').val()){
            document.getElementById('inputerror').style.display="block";
            document.getElementById('inputerror').innerHTML="Latino min population > max!";
            e.preventDefault();
        } else {

            // get form data
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
                compactnessWeight: $('#compactness').val(),
                partisanFairnessWeight: $('#partisanFairness').val(),
                equalPopulationWeight: $('#equalPopulation').val(),
                numberOfDistrict: $('#numOfDistrict').val(),
                ethnicGroupNumber: mm_data,
                ethnicGroupBound: enthnic_data
            };

            e.preventDefault();

            // show current preference data
            fakeLog("Current preference:");
            fakeLog("&nbspWeight of compactness:" + preference_data.compactnessWeight);
            fakeLog("&nbspWeight of partisan fairness:" + preference_data.partisanFairnessWeight);
            fakeLog("&nbspWeight of equal population:" + preference_data.equalPopulationWeight);
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

            if (document.getElementById('sep_process').checked) {
                // show phase with process
                $.ajax({
                    type: 'post',
                    url: '/home/main/phaseOneWithUpdate',
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify(preference_data),
                    dataType: "json",
                    success: function (data) {
                        console.log("phase one process start....");
                        ajaxPhaseI();
                    }
                })
            } else {
                // start phase I without process
                $.ajax({
                    type: 'post',
                    url: '/home/main/startPhaseOne',
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify(preference_data),
                    dataType: "json",
                    success: function (data) {
                        if (map.hasLayer(districtLayer)) {
                            map.removeLayer(districtLayer);
                        }
                        if (map.hasLayer(precinctLayer)) {
                            map.removeLayer(precinctLayer);
                        }
                        if (Object.keys(data)[0].charAt(0) == '1') {
                            districtLayer = L.geoJSON(oh_data.FeatureCollection, {
                                onEachFeature: clusterOnEachFeature,
                                style: function (feature) {
                                    return {
                                        fillColor: data[feature.properties.id], fillOpacity: 1,
                                        opacity: 1, weight: 1, color: data[feature.properties.id]
                                    };
                                }
                            }).addTo(map);
                            precinctLayer = L.geoJSON(oh_data.FeatureCollection, {
                                onEachFeature: precinctOnEachFeature,
                                style: function (feature) {
                                    return {weight: 2,opacity: 1, dashArray: '3',
                                      fillOpacity: 0.7, color: 'white',
                                      fillColor: data[feature.properties.id]
                                    };
                                }
                            }).addTo(map);
                        } else if (Object.keys(data)[0].charAt(0) == '2') {
                            districtLayer = L.geoJSON(NY_precinctsData.FeatureCollection, {
                                onEachFeature: clusterOnEachFeature,
                                style: function (feature) {
                                    return {
                                        fillColor: data[feature.properties.id], fillOpacity: 1,
                                        opacity: 1, weight: 1, color: data[feature.properties.id]
                                    };
                                }
                            }).addTo(map);
                            precinctLayer = L.geoJSON(NY_precinctsData.FeatureCollection, {
                                onEachFeature: precinctOnEachFeature,
                                style: function (feature) {
                                    return {weight: 2,opacity: 1, dashArray: '3',
                                      fillOpacity: 0.7, color: 'white',
                                      fillColor: data[feature.properties.id]
                                    };
                                }
                            }).addTo(map);
                        } else {
                            districtLayer = L.geoJSON(IA_precinctsData.FeatureCollection, {
                                onEachFeature: clusterOnEachFeature,
                                style: function (feature) {
                                    return {
                                        fillColor: data[feature.properties.id], fillOpacity: 1,
                                        opacity: 1, weight: 1, color: data[feature.properties.id]
                                    };
                                }
                            }).addTo(map);
                            precinctLayer = L.geoJSON(IA_precinctsData.FeatureCollection, {
                                onEachFeature: precinctOnEachFeature,
                                style: function (feature) {
                                    return {weight: 2,opacity: 1, dashArray: '3',
                                      fillOpacity: 0.7, color: 'white',
                                      fillColor: data[feature.properties.id]
                                    };
                                }
                            }).addTo(map);
                        }

                        // enable phase2 button to start simulating annealing
                        $('#phase2').prop('disabled', false);
                        $('#phase1').prop('disabled', true);
                    }
                })
            }

            // start to get the change of phase1
            function ajaxPhaseI() {
                $.ajax({
                    type: 'post',
                    url: "/home/main/getPhaseIChange",
                    contentType: "application/json; charset=utf-8",
                    header: {"accept": "application/json"},
                    dataType: "json",
                    success: function (data) {
                        console.log("if end? 0 is end: "+Object.keys(data)[0]);
                        console.log("p id list: "+Object.keys(data));
                        console.log("first color: "+data[Object.keys(data)[0]]);
                        if (data[Object.keys(data)[0]] == 'end') {
                            // after phase 1 finish, enable playphase2
                            $('#phase1').prop('disabled', true);
                            $('#phase2').prop('disabled', false);
                        } else {
                            // update GUI
                            if (map.hasLayer(districtLayer)) {
                                map.removeLayer(districtLayer);
                            }
                            if (map.hasLayer(precinctLayer)) {
                                map.removeLayer(precinctLayer);
                            }
                            if (Object.keys(data)[0].charAt(0) == '1') {
                                districtLayer = L.geoJSON(oh_data.FeatureCollection, {
                                    onEachFeature: clusterOnEachFeature,
                                    style: function (feature) {
                                        return {
                                            fillColor: data[feature.properties.id], fillOpacity: 1,
                                            opacity: 1, weight: 1, color: data[feature.properties.id]
                                        };
                                    }
                                }).addTo(map);
                                precinctLayer = L.geoJSON(oh_data.FeatureCollection, {
                                    onEachFeature: precinctOnEachFeature,
                                    style: function (feature) {
                                        return {weight: 2,opacity: 1, dashArray: '3',
                                          fillOpacity: 0.7, color: 'white',
                                          fillColor: data[feature.properties.id]
                                        };
                                    }
                                }).addTo(map);
                            } else if (Object.keys(data)[0].charAt(0) == '2') {
                                districtLayer = L.geoJSON(NY_precinctsData.FeatureCollection, {
                                    onEachFeature: clusterOnEachFeature,
                                    style: function (feature) {
                                        return {
                                            fillColor: data[feature.properties.id], fillOpacity: 1,
                                            opacity: 1, weight: 1, color: data[feature.properties.id]
                                        };
                                    }
                                }).addTo(map);
                                precinctLayer = L.geoJSON(NY_precinctsData.FeatureCollection, {
                                    onEachFeature: precinctOnEachFeature,
                                    style: function (feature) {
                                        return {weight: 2,opacity: 1, dashArray: '3',
                                          fillOpacity: 0.7, color: 'white',
                                          fillColor: data[feature.properties.id]
                                        };
                                    }
                                }).addTo(map);
                            } else {
                                districtLayer = L.geoJSON(IA_precinctsData.FeatureCollection, {
                                    onEachFeature: clusterOnEachFeature,
                                    style: function (feature) {
                                        return {
                                            fillColor: data[feature.properties.id], fillOpacity: 1,
                                            opacity: 1, weight: 1, color: data[feature.properties.id]
                                        };
                                    }
                                }).addTo(map);
                                precinctLayer = L.geoJSON(IA_precinctsData.FeatureCollection, {
                                    onEachFeature: precinctOnEachFeature,
                                    style: function (feature) {
                                        return {weight: 2,opacity: 1, dashArray: '3',
                                          fillOpacity: 0.7, color: 'white',
                                          fillColor: data[feature.properties.id]
                                        };
                                    }
                                }).addTo(map);
                            }
                            // continue send ajax call
                            ajaxPhaseI();
                        }
                    }
                });
            }
            function ajaxPhase2(){
                // start phase 2
                $.ajax({
                    type: 'post',
                    url: "/home/main/startPhaseTwo",
                    contentType: "application/json; charset=utf-8",
                    header: {"accept": "application/json"},
                    dataType: "json",
                    success: function (data) {
                        console.log("phase2 ... " + data);
                    }
                })
            }
            // start to get the change of phase2
            function ajaxPhaseII() {
                $.ajax({
                    type: 'post',
                    url: "/home/main/getPhaseIIChange",
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
                            ajaxPhaseII();
                        }
                    }
                });
            }
        }
    })
});
