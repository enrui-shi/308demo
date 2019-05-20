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


var districtLayer;
var precinctLayer;

/* add data into map */
/* way one
$.getJSON("../data/OH_final.js" , function( result ){
    L.geoJSON(result.features, {
    style: function(feature) {
        //style: myStyle
    }
}).addTo(map);
});*/
/* way two */
/*OH_precinctLayer = L.geoJSON(OH_precinctsData.FeatureCollection, {
    style: precinctStyle,
    onEachFeature: precinctOnEachFeature
}).addTo(map);*/



/* select state */
function selectOH(){
    if(map.hasLayer(stateLayer))
        map.removeLayer(stateLayer);
    document.getElementById("myStateDropDown").style.display = "none";
    if($.cookie('currentuser') != "" && $.cookie('currentuser') != undefined) {
        $('#menubtn').prop('disabled', false);
    }
    map.setView([40.4173, -82.9071], 9);

    if(map.hasLayer(precinctLayer)) {
      map.removeLayer(precinctLayer);
    }
    if(map.hasLayer(districtLayer)){
        map.removeLayer(districtLayer);
    }

    console.log("load OH data ing...")

    precinctLayer = L.geoJSON(oh_data.FeatureCollection, {
        style: precinctStyle,
        onEachFeature: precinctOnEachFeature
    }).addTo(map);

    districtLayer = L.geoJSON(OH_districtsData.FeatureCollection, {
        style: districtStyle,
        onEachFeature: districtOnEachFeature
    }).addTo(map);

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
    if($.cookie('currentuser') != "" && $.cookie('currentuser') != undefined) {
        $('#menubtn').prop('disabled', false);
    }
    map.setView([40.7128, -74.0060], 9);

    if(map.hasLayer(precinctLayer))
        map.removeLayer(precinctLayer);
    if(map.hasLayer(districtLayer))
        map.removeLayer(districtLayer);

    precinctLayer = L.geoJSON(NY_precinctsData.FeatureCollection, {
        style: precinctStyle,
        onEachFeature: precinctOnEachFeature
    }).addTo(map);

    districtLayer = L.geoJSON(NY_districtsData.FeatureCollection, {
        style: districtStyle,
        onEachFeature: districtOnEachFeature
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

function selectIA(){
    if(map.hasLayer(stateLayer))
        map.removeLayer(stateLayer);
    document.getElementById("myStateDropDown").style.display = "none";
    if($.cookie('currentuser') != "" && $.cookie('currentuser') != undefined) {
        $('#menubtn').prop('disabled', false);
    }
    map.setView([41.8780, -93.0977], 9);

    if(map.hasLayer(precinctLayer))
        map.removeLayer(precinctLayer);
    if(map.hasLayer(districtLayer))
        map.removeLayer(districtLayer);

    precinctLayer = L.geoJSON(IA_precinctsData.FeatureCollection, {
        style: precinctStyle,
        onEachFeature: precinctOnEachFeature
    }).addTo(map);

    districtLayer = L.geoJSON(IA_districtsData.FeatureCollection, {
        style: districtStyle,
        onEachFeature: districtOnEachFeature
    }).addTo(map);

    $.ajax({
        type: 'post',
        url: "/home/main/createState?stateName=IA",
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
            '<b>District ID: ' + props.id + '</b><br />Population of AfricanAmerican: ' + props.AfricanAmerican
            +'</b><br />Total population: ' + props.total
            +'</b><br />Population of Hispanic: ' + props.Hispanic
            +'</b><br />Population of India: ' + props.Indian
            +'</b><br />Population of Asian: ' + props.Asian
            +'</b><br />Population of White: ' + props.White
            : 'Hover over a district');
    }else if(layer == precinctLayer) {
        this._div.innerHTML = '<h4>Precinct</h4>' +  (props ?
            '<b>' + '</b><br /> id: ' + props.properties.id
            + '</b><br /> total voting: ' + props.properties.total_vote
            + '</b><br /> Democratic: ' + props.properties.d_vote
            + '</b><br /> Republican: ' + props.properties.r_vote
            : 'Hover over a precinct');
    } else {
        this._div.innerHTML = '<h4>State</h4>' +  (props ?
            '<b>State Name: ' + props.properties.name
            : 'Hover over a state');
    }
};

info.addTo(map);


function precinctStyle(feature) {
    return {
        weight: 2,
        opacity: 1,
        color: 'white',
        dashArray: '3',
        fillOpacity: 0.7,
        fillColor: 'white'
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

function getOriginalColor() {
    var picker = ['0','1','2','3','4','5','6','7','8','9','a','b','c','e','f'];
    var color1 = '#';
    for(var count=0; count<6; count++){
        var random = Math.floor(Math.random() * 15);
        color1 += picker[random];
    }
    console.log("random pick color "+color1);
    return color1;
}

// set up district style
function districtStyle(feature) {
    return {
        opacity: 1,
        dashArray: '3',
        fillOpacity: 0.7,
        fillColor: getOriginalColor()
    };
}

function districtHoverFeature(e) {
    var layer = e.target;
    layer.setStyle({
        dashArray: '',
        fillOpacity: 0.3
    });
    if (!L.Browser.ie && !L.Browser.opera && !L.Browser.edge) {
        layer.bringToFront();
    }
    info.update(layer.feature.properties, districtLayer);
}

function resetDistrict(e) {
    e.target.setStyle( {
        opacity: 1,
        dashArray: '3',
        fillOpacity: 0.7
    });
    info.update(null, districtLayer);
}

// set up district style
function getStateColor(n) {
    switch (n) {
        case 'Iowa': return "#ff0000";
        case 'Ohio': return "#ff6600";
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

function clusterHoverFeature(e) {
    var layer = e.target;

    if (!L.Browser.ie && !L.Browser.opera && !L.Browser.edge) {
        layer.bringToFront();
    }

    // hover 2 seconds to display demographic data of the precinct
    /*setTimeout(function(){
        console.log("hover two seconds to show demographic data");
        $.ajax({
            type: 'post',
            url: "/home/main/showDemo?precinctID="+layer.feature.properties.id,
            contentType:"application/json; charset=utf-8",
            header: {"accept": "application/json"},
            dataType:"json",
            success: function (data){
                console.log(data);

                var popContent;
                if(data.Demographic == 'Undefined'){
                    popContent = "<b>demographic in precinct "+ layer.feature.properties.id + "</b><br>Do not find its demographic data";
                    layer.bindPopup(popContent);
                    layer.on('mouseover', function (e) {
                        this.openPopup();
                    });
                } else {
                    popContent = "<b>demographic in precinct"+ layer.feature.properties.id + "</b>"
                        + "<br>totalPopulation: "+data.totalPopulation + "<br>ASIAN_PACIFIC: "+data.ASIAN_PACIFIC
                        + "<br>LATINO: "+data.LATINO + "<br>WHITE: "+data.WHITE + "<br>AFRIAN_AMERICAN: "+data.AFRIAN_AMERICAN;

                    layer.bindPopup(popContent);
                    layer.on('mouseover', function (e) {
                        this.openPopup();
                    });
                }
            }
        })
    }, 2000);*/
}

function resetCluster(e) {
    e.target.setStyle( {
    });
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

function clusterOnEachFeature(feature, layer) {
    layer.on({
        mouseover: clusterHoverFeature,
        mouseout: resetCluster,
        click: zoomToFeature
    });
}

/* show different layers when zoom in/out */
map.on('zoomend', function () {
    currentZoom = map.getZoom();

    if (currentZoom < 8) {
        // show district boundary
        if(map.hasLayer(precinctLayer)) {
            map.removeLayer(precinctLayer);
        }
        map.addLayer(districtLayer);
    }
    else if(currentZoom <= 9 && currentZoom >=8){
        // show both
        map.addLayer(districtLayer);
        //map.addLayer(precinctLayer);
    }
    else{
        // show precinct boundary
        if(map.hasLayer(districtLayer))
            map.removeLayer(districtLayer);
        map.addLayer(precinctLayer);
    }
});
