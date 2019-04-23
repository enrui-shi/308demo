        //init map
        document.onload = function loadMap(){
            var mymap = L.map('mapid').setView([51.505, -0.09], 13);
        }
        function openSidebar() {
            document.getElementById("main").style.marginRight = "30%";
            document.getElementById("sidebar").style.width = "30%";
            document.getElementById("sidebar").style.display = "block";
            document.getElementById("sidebar").style.animation = "popup 0.5s";
        }
        function closeSidebar() {
            document.getElementById("main").style.marginRight = "0%";
            document.getElementById("sidebar").style.animation = "popin 0.5s"; 
            document.getElementById("sidebar").style.display = "none"; 
        }
        function updateTextInput(val, id) {
            document.getElementById(id + "Value").innerHTML = val / 100;
            if (val == 100) {
                document.getElementById(id + "Value").innerHTML = "1.00";
            }
            else if (val == 0) {
                document.getElementById(id + "Value").innerHTML = "0.00";
            }
            else if (val % 10 == 0) {
                document.getElementById(id + "Value").innerHTML = val / 100 + "0";
            }
        }  
        function sideSetting(){
            if(document.getElementById("setting").style.display == "block"){
                document.getElementById("setting").style.display = "none";
            }else{
                document.getElementById("setting").style.display = "block";
            }
        }                                      
