<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" type="text/css" href="style/style.css">
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://unpkg.com/leaflet@1.4.0/dist/leaflet.css"
        integrity="sha512-puBpdR0798OZvTTbP4A8Ix/l+A4dHDD0DGqYW6RQ+9jxkRFclaxxQb/SJAWZfWAkuyeQUytO7+7N4QKrDh+drA=="
        crossorigin=""/>
        <script src="https://unpkg.com/leaflet@1.4.0/dist/leaflet.js"
        integrity="sha512-QVftwZFqvtRNi0ZyCtsznlKSWOStnDORoefr1enyq5mVL4tmKB3S/EnC3rRJcxCPavG10IcrVGSmPh6Qw5lwrg=="
        crossorigin=""></script>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <title>Main Page</title>

        <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
        <script src="/script/slidebar.js"></script>
        <script src="/script/main.js"></script>
        <script src="/script/selectState.js"></script>
        <script src="/script/logout.js"></script>
        <script src="/script/guest.js"></script>
    </head>
    <header class="topnav">
        <a class="active" onclick="window.location.replace('/')">Home</a>
        <a id="guest_b" onclick="window.location.replace('/batch')" disabled>Batch</a>
        <a id="guest_s" onclick="window.location.replace('/summary')" disabled>Summary</a>
        <a id="guest_a" onclick="window.location.replace('/about')" disabled>About</a>
        <input type="image" class="user-icon" title="user login or logout" onclick="clickProfile()" src="image/user_icon.jpg" />
    </header>
    <body>
        <div id="user01" class="my-modal">
            <div class="my-modal-content w3-animate-zoom "><br>
                <div class="w3-center">
                    <span onclick="document.getElementById('user01').style.display='none'" class="close w3-button w3-small w3-hover-red w3-display-topright" title="Close Modal">&times;</span>
                    <p><button class="button" id="in" onclick="window.location.replace('/login')">Login</button></p>
                    <p><button class="button" id="out" onclick="logout()">Logout</button></p>
                </div>
            </div>
        </div>

        <div id="main">
            <div class="map-content">
                <div id="map"></div>
                <script type = "text/javascript" src = "/data/state.js"></script>
                <script type = "text/javascript" src = "/data/oh_temp.js"></script>
                <script type = "text/javascript" src = "/data/NY_precincts.js"></script>
                <script type = "text/javascript" src = "/data/IA_precincts.js"></script>
                <script type = "text/javascript" src = "/data/OH_districts.js"></script>
                <script type = "text/javascript" src = "/data/NY_districts.js"></script>
                <script type = "text/javascript" src = "/data/IA_districts.js"></script>
                <script src = "/script/leafletmap.js"></script>
            </div><!--map-content div over-->
            <div class="my-menu">
                <button id="menubtn" onclick= "openSidebar();" title="Input your preference data" disabled><i class="fa fa-bars"></i> Menu</button>
            </div><!--my-menu div over-->
            <div class="select-state" id="select-state">
                <button onclick="dropDownSelection();" class="select-state-btn"><i class="fa fa-caret-down"></i> Select State</button>
                <div id="myStateDropDown" class="dropdown-content">
                    <input onclick="selectNY()" type="button" id="NY" value="New York">
                    <input onclick="selectOH()" type="button" id="OH" value="Ohio">
                    <input onclick="selectIA()" type="button" id="IA" value="Iowa">
                </div>
            </div><!--select-state div over-->
            <div class="aa-color-set" id="aa-color-set">
                <button id="aa-color-btn" onclick= "setAAPDcolor();" style="display: none;"><i class="fa fa-asterisk"></i> View African-American Population%</button>
            </div>
            <div class="aa-color-set" style="top:60%" id="mm-color-set">
                <button id="mm-color-btn" onclick= "setMMcolor();" style="display: none;"><i class="fa fa-asterisk"></i> View majority-minority district</button>
            </div>
            <div class="aa-color-set" style="top:75%" id="original-color-set">
                <button id="original-color-btn" onclick= "setOriginalcolor();" style="display: none;"><i class="fa fa-history"></i> View original phase two result</button>
            </div>
            <div id = "sidebar">
                <button onclick = "closeSidebar();" class = "icon"><i class="fa fa-close"></i></button>
                <button onclick="sideSetting();" class = "icon" title="term explain"><i class = "fa fa-question-circle"></i></button>
                <div class = "setting" id = "setting">
                    <div class="arrow-up"></div>
                    <h3>Term explainning</h3>
                    <p>LW Compactness:Compare ratio of Weight and Height of the minimum bounding rectangle, ratio closer to 1 more compact</p>
                    <p>Simple Compactness: Compare inner precincts number and bound precinct number</p>
                    <p>Partisan Fairnes:Measure efficiency gap</p>

                </div><!--setting div over-->
                <form id="preference">
                    <div class="my-slidebar-box">
                        <div class="my-slidebar-box-b">
                            <input type="range" min="0" max="100" value="0" id="compactness"
                                   onchange="updateTextInput(this.value,this.id);">
                            <label>&emsp;</label>
                            <label id="compactnessValue">0.00</label></u><label>&emsp;Simple Compactness</label> <br>
                            <input type="range" min="0" max="100" value="0" id="partisanFairness"
                                   onchange="updateTextInput(this.value,this.id);">
                            <label>&emsp;</label>
                            <u><label id="partisanFairnessValue">0.00</label></u><label>&emsp;Partisan Fairness</label><br>

                            <input type="range" min="0" max="100" value="0" id="equalPopulation"
                                   onchange="updateTextInput(this.value,this.id);">
                            <label>&emsp;</label>
                            <label id="equalPopulationValue">0.00</label></u><label>&emsp;Equal Population</label><br>
                            <input type="range" min="0" max="100" value="0" id="lengthWidth"
                                   onchange="updateTextInput(this.value,this.id);">
                            <label>&emsp;</label>
                            <label id="lengthWidthValue">0.00</label></u><label>&emsp;LW Compactness</label> <br>
                        </div>
                    </div><!--my-slidebar-box div over-->
                    <div class="my-numberInput">
                        <span id="inputerror" style="color:red"></span>
                        <label>number of district</label> <input type="number" id="numOfDistrict" min="0" max="200000" value="0"><br><p></p>
                        <label style="color: blue;">ethnic group: African-American</label><br><p></p>
                        <label>number of majority-minority district</label> <input type="number" min="0" max="200000" id="mmAA" value="0"> <br><p></p>
                        <label>min population (%)</label> <input type="number" id="minAA" min="0" max="100" value="0"><br><p></p>
                        <label>max population (%)</label> <input type="number" id="maxAA" min="0" max="100" value="0"><br><p></p>
                        <label style="color: orange;">ethnic group: ASIAN_PACIFIC</label><br><p></p>
                        <label>number of majority-minority district</label> <input type="number" id="mmAsian" min="0" max="200000" value="0"> <br><p></p>
                        <label>min population (%)</label> <input type="number" id="minAsian" min="0" max="100" value="0"><br><p></p>
                        <label>max population (%)</label> <input type="number" id="maxAsian" min="0" max="100" value="0"><br><p></p>
                        <label style="color: red">ethnic group: Latino</label><br><p></p>
                        <label>number of majority-minority district</label> <input type="number" id="mmLatino" min="0" max="200000" value="0"> <br><p></p>
                        <label>min population (%)</label> <input type="number" id="minLatino" min="0" max="100" value="0"><br><p></p>
                        <label>max population (%)</label> <input type="number" id="maxLatino" min="0" max="100" value="0"><br><p></p>
                    </div><!--my-numberInput div over-->
                    <div class="my-map-checkbox-container">
                        <input type="checkbox" id="sep_process"> Run Phase I with a GUI update at the end of each discrete process<br>
                    </div>
                    <div class="my-map-button-container">
                        <button id="phase1" class="my-map-btn" type="submit" title="Run Phase I with a GUI update at the end."><i class="fa fa-play"></i> Play Phase One</button>
                        <label>&emsp;</label>
                        <button id="phase2" class="my-map-btn" onclick="ajaxPhaseII();return false;" title="Run Phase II: simulating annealing" disabled><i class="fa fa-play"></i> Play Phase Two</button>
                    </div><!--my-map-button-container div over-->
                    <div class="my-console-container">
                        <p id="log"></p>
                    </div>
                </form>
            </div><!--sidebar div over-->
        </div><!--main div over-->
    </body>
 </html>
