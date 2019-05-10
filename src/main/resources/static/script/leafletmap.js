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
}).addTo(map);

var districtLayer;
var precinctLayer;


/* select state */
function selectOH(){
    if(map.hasLayer(stateLayer))
        map.removeLayer(stateLayer);
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
    precinctLayer = L.geoJSON(precinctsData.map, {
        style: precinctStyle,
        onEachFeature: precinctOnEachFeature
    }).addTo(map);
    districtLayer = L.geoJSON(districtsData,{
        style: districtStyle,
        onEachFeature: districtOnEachFeature
    }).addTo(map);
}

function selectNY(){
    if(map.hasLayer(stateLayer))
        map.removeLayer(stateLayer);
    if($.cookie('currentuser') != "") {
        $('#menubtn').prop('disabled', false);
    }
    map.setView([40.7128, -74.0060], 9);
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
    precinctLayer = L.geoJSON(NY_precinctsData.FeatureCollection, {
        style: precinctStyle,
        onEachFeature: precinctOnEachFeature
    }).addTo(map);
}

function selectNJ(){
    if(map.hasLayer(stateLayer))
        map.removeLayer(stateLayer);
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
    }else{
        this._div.innerHTML = '<h4>Precinct</h4>' +  (props ?
            '<b>' + props.name + '</b><br /> total voting: ' + props.total_vote
            + '</b><br /> Democratic: ' + props.d_vote
            + '</b><br /> Republican: ' + props.r_vote
            : 'Hover over a precinct');
    }
};

info.addTo(map);

/* set up map style */
function getPrecinctColor(w) {
    switch (w) {
        case 'Republican': return "#ff6666"; // red
        case 'Democratic': return "#66b2ff"; // blue
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

function districtStyle(feature) {
    return {
        weight: 2,
        opacity: 1,
        color: 'black',
        dashArray: '3',
        fillOpacity: 0.1,
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
    info.update(layer.feature.properties, precinctLayer);
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

function zoomToFeature(e) {
    map.fitBounds(e.target.getBounds());
}

/* set up event */
function precinctOnEachFeature(feature, layer) {
    layer.on({
        mouseover: precinctHoverFeature,
        mouseout: resetPrecinct,
        click: zoomToFeature
    });
}
function districtOnEachFeature(feature, layer) {
    layer.on({
        //mouseover: districtHoverFeature,
        //mouseout: resetdistrict,
        click: zoomToFeature
    });
}


/* show different layers when zoom in/out */
map.on('zoomend', function () {
    currentZoom = map.getZoom();
    if (currentZoom < 8) {
        /* set up district boundary*/
        if(map.hasLayer(precinctLayer))
            map.removeLayer(precinctLayer);
        map.addLayer(districtLayer);
    }
    else if(currentZoom <= 9 && currentZoom >=8){
        map.addLayer(districtLayer);
        map.addLayer(precinctLayer);
    }
    else{
        if(map.hasLayer(districtLayer))
            map.removeLayer(districtLayer);
        map.addLayer(precinctLayer);
    }
});