/* initialize the map */
var map = L.map('map').setView([37, -94], 4.5);

/* load a tile layer(worldwide) */
L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
    maxZoom: 18,
    attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' +
        '<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
        'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
    id: 'mapbox.light'
}).addTo(map);


var stateLayer = L.geoJSON(statesData,{
    style: stateStyle,
    onEachFeature: stateOnEachFeature
}).addTo(map);


var OH_districtLayer;
var OH_precinctLayer;
var NY_districtLayer;
var NY_precinctLayer;
var NJ_districtLayer;
var NJ_precinctLayer;

var precinctLayer;
var districtLayer;

/* add data into map */
/* way one
$.getJSON("../data/OH_final.json" , function( result ){
    L.geoJSON(result.features, {
    style: function(feature) {
        //style: myStyle
    }
}).addTo(map);
});*/
/* way two */
OH_precinctLayer = L.geoJSON(OH_precinctsData.FeatureCollection, {
    style: precinctStyle,
    onEachFeature: precinctOnEachFeature
}).addTo(map);

NY_precinctLayer = L.geoJSON(NY_precinctsData.FeatureCollection, {
    style: precinctStyle,
    onEachFeature: precinctOnEachFeature
}).addTo(map);

NJ_precinctLayer = L.geoJSON(NJ_precinctsData.FeatureCollection, {
    style: precinctStyle,
    onEachFeature: precinctOnEachFeature
}).addTo(map);

OH_districtLayer = L.geoJSON(districtsData.features, {
    style: districtStyle,
    onEachFeature: districtOnEachFeature
}).addTo(map);

/* select state */
function selectOH(){
    if(map.hasLayer(stateLayer))
        map.removeLayer(stateLayer);
    document.getElementById("myStateDropDown").style.display = "none";
    if($.cookie('currentuser') != "") {
        $('#menubtn').prop('disabled', false);
    }
    map.setView([40.4173, -82.9071], 9);

    $.ajax({
        type: 'post',
        url: "/home/main/createState?stateName=OH",
        contentType:"application/json; charset=utf-8",
        header: {"accept": "application/json"},
        dataType:"json",
        success: function (data){
            console.log(data);
        }
    })

}

function selectNY(){
    if(map.hasLayer(stateLayer))
        map.removeLayer(stateLayer);
    document.getElementById("myStateDropDown").style.display = "none";
    if($.cookie('currentuser') != "") {
        $('#menubtn').prop('disabled', false);
    }
    map.setView([40.7128, -74.0060], 9);

    precinctLayer = L.geoJSON(NY_precinctsData.FeatureCollection, {
        style: precinctStyle,
        onEachFeature: precinctOnEachFeature
    }).addTo(map);

    $.ajax({
        type: 'post',
        url: "/home/main/createState?stateName=NY",
        contentType:"application/json; charset=utf-8",
        header: {"accept": "application/json"},
        dataType:"json",
        success: function (data){
            console.log(data);
        }
    })
}

function selectNJ(){
    if(map.hasLayer(stateLayer))
        map.removeLayer(stateLayer);
    document.getElementById("myStateDropDown").style.display = "none";
    if($.cookie('currentuser') != "") {
        $('#menubtn').prop('disabled', false);
    }
    map.setView([40.0583, -74.4057], 9);

    $.ajax({
        type: 'post',
        url: "/home/main/createState?stateName=NJ",
        contentType:"application/json; charset=utf-8",
        header: {"accept": "application/json"},
        dataType:"json",
        success: function (data){
            console.log(data);
        }
    })
}


/* control that shows precinct info on hover */
var info = L.control();

info.onAdd = function (map) {
    this._div = L.DomUtil.create('div', 'info');
    this.update();
    return this._div;
};

info.update = function (props, layer) {
    if(layer == districtLayer){
        this._div.innerHTML = '<h4>District</h4>' +  (props ?
            '<b>District Name: ' + props.name + '</b><br />Population: ' + props.demographic
            : 'Hover over a district');
    }else if(layer == precinctLayer) {
        this._div.innerHTML = '<h4>Precinct</h4>' +  (props ?
            '<b>' + props.name + '</b><br /> id: ' + props.id
            + '</b><br /> total voting: ' + props.properties.total_vote
            + '</b><br /> Democratic: ' + props.properties.d_vote
            + '</b><br /> Republican: ' + props.properties.r_vote
            : 'Hover over a precinct');
    } else {
        this._div.innerHTML = '<h4>State</h4>' +  (props ?
            '<b>State Name: ' + props.name
            : 'Hover over a state');
    }
};

info.addTo(map);

// set up precinct style
function getPrecinctColor(w) {
    switch (w) {
        case 'Republican': return "#ff6666";
        case 'Democratic': return "#66b2ff";
    }
}

function precinctStyle(feature) {
    return {
        weight: 2,
        opacity: 1,
        color: 'white',
        dashArray: '3',
        fillOpacity: 0.7,
        fillColor: getPrecinctColor(feature.properties.winner)
    };
}

/* mouse hover */
function precinctHoverFeature(e) {
    var layer = e.target;
    layer.setStyle({
        weight: 3,
        color: '#666',
        dashArray: '',
        fillOpacity: 0.7
    });
    if (!L.Browser.ie && !L.Browser.opera && !L.Browser.edge) {
        layer.bringToFront();
    }
    info.update(layer.feature, precinctLayer);

    // hover 2 seconds to display demographic data of the precinct
    setTimeout(function(){
        console.log("hover two seconds to show demographic data");
        $.ajax({
            type: 'post',
            url: "/home/main/showDemo?precinctID="+layer.feature.properties.id,
            contentType:"application/json; charset=utf-8",
            header: {"accept": "application/json"},
            dataType:"json",
            success: function (data){
                console.log(data);
                alert("success");
            }
        })
    }, 2000);
}

/* mouse remove */
function resetPrecinct(e) {
    e.target.setStyle( {
        weight: 2,
        opacity: 1,
        color: 'white',
        dashArray: '3',
        fillOpacity: 0.7
    });
    info.update(null, precinctLayer);
}

// set up district style
function districtStyle(feature) {
    return {
        weight: 2,
        opacity: 1,
        color: 'black',
        dashArray: '3',
        fillOpacity: 0.1
    };
}

function districtHoverFeature(e) {
    var layer = e.target;
    layer.setStyle({
        weight: 3,
        color: '#666',
        dashArray: '',
        fillOpacity: 0.7
    });
    if (!L.Browser.ie && !L.Browser.opera && !L.Browser.edge) {
        layer.bringToFront();
    }
    info.update(layer.feature, districtLayer);
}

function resetDistrict(e) {
    e.target.setStyle( {
        weight: 2,
        opacity: 1,
        color: 'white',
        dashArray: '3',
        fillOpacity: 0.7
    });
    info.update(null, districtLayer);
}

// set up district style
function getStateColor(n) {
    switch (n) {
        case 'Ohio': return "#ff0000";
        case 'New Jersey': return "#ff6600";
        case 'New York': return "#ffff00";
    }
}

function stateStyle(feature) {
    return {
        weight: 2,
        opacity: 1,
        color: 'black',
        dashArray: '3',
        fillOpacity: 0.7,
        fillColor: getStateColor(feature.properties.name)
    };
}

function stateHoverFeature(e) {
    var layer = e.target;
    layer.setStyle({
        weight: 3,
        color: '#666',
        dashArray: '',
        fillOpacity: 0.1
    });
    if (!L.Browser.ie && !L.Browser.opera && !L.Browser.edge) {
        layer.bringToFront();
    }
    info.update(layer.feature, stateLayer);
}

function resetState(e) {
    e.target.setStyle( {
        weight: 2,
        opacity: 1,
        color: 'white',
        dashArray: '3',
        fillOpacity: 0.7
    });
    info.update(null, stateLayer);
}

function zoomToFeature(e) {
    map.fitBounds(e.target.getBounds());
}

/* set up event */
function stateOnEachFeature(feature, layer) {
    layer.on({
        mouseover: stateHoverFeature,
        mouseout: resetState,
        click: zoomToFeature
    });
}

function precinctOnEachFeature(feature, layer) {
    layer.on({
        mouseover: precinctHoverFeature,
        mouseout: resetPrecinct,
        click: zoomToFeature
    });
}

function districtOnEachFeature(feature, layer) {
    layer.on({
        mouseover: districtHoverFeature,
        mouseout: resetDistrict,
        click: zoomToFeature
    });
}


/* show different layers when zoom in/out */
map.on('zoomend', function () {
    currentZoom = map.getZoom();
    if (currentZoom < 8) {
        // show district boundary
        if(map.hasLayer(OH_precinctLayer))
            map.removeLayer(OH_precinctLayer);
        map.addLayer(OH_districtLayer);
        if(map.hasLayer(NY_precinctLayer))
            map.removeLayer(NY_precinctLayer);
        map.addLayer(NY_districtLayer);
        if(map.hasLayer(NJ_precinctLayer))
            map.removeLayer(NJ_precinctLayer);
        map.addLayer(NJ_districtLayer);
    }
    else if(currentZoom <= 9 && currentZoom >=8){
        // show both
        map.addLayer(OH_districtLayer);
        map.addLayer(OH_precinctLayer);
        map.addLayer(NY_districtLayer);
        map.addLayer(NY_precinctLayer);
        map.addLayer(NJ_districtLayer);
        map.addLayer(NJ_precinctLayer);
    }
    else{
        // show precinct boundary
        if(map.hasLayer(OH_districtLayer))
            map.removeLayer(OH_districtLayer);
        map.addLayer(OH_precinctLayer);
        if(map.hasLayer(NY_districtLayer))
            map.removeLayer(NY_districtLayer);
        map.addLayer(NY_precinctLayer);
        if(map.hasLayer(NJ_districtLayer))
            map.removeLayer(NJ_districtLayer);
        map.addLayer(NJ_precinctLayer);
    }
});