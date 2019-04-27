<html>
    <head>
        <link rel="stylesheet" type="text/css" href="style/batchstyle.css">
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="script/batch.js"></script>
        <title>BatchRun</title>
    </head>
    <header>
        <div class="topnav">
            <a onclick="window.location.replace('/main')">Home</a>
            <a onclick="window.location.href='map.html'">Map</a>
            <a onclick="window.location.href='report.html'">Report</a>
            <a class="active" onclick="window.location.replace('/batch')">Batch</a>
            <a onclick="window.location.href='about.html'">About</a>
            <input type="image" class="user-icon" onclick="document.getElementById('user01').style.display='block'" src="image/user_icon.jpg" />
        </div>
    </header>
    <body>
        <div id="user01" class="my-modal">
            <div class="my-modal-content w3-animate-zoom "><br>
                <div class="w3-center">
                    <span onclick="document.getElementById('user01').style.display='none'" class="close w3-button w3-small w3-hover-red w3-display-topright" title="Close Modal">&times;</span>
                    <p><button class="button" type="submit">Change Password</button></p>
                    <p><button class="button" type="submit" onclick="window.location.replace('/')">Logout</button></p>
                </div>
            </div>
        </div>

        <form id="batch">
            <fieldset>
                <legend>Batch Run</legend>
                <label class = "textField"> STATE:</label>
                <b>
                    NY <input type="radio" name="state" value="NY">
                    NJ <input type="radio" name="state" value="NJ">
                    OH <input type="radio" name="state" value="OH"><br>
                </b>
                <label class = "textField"> Number of batch:</label>
                <b> <input id="num-of-batch" class="inputNum" type="number" step="1"> <br></b>
                <label class = "textField">Number of majority minority:</label>
                <b>
                    <input type="number" class="range" name="mmMIN" step="1" min = "0" max = "10000000"> to
                    <input type="number" class="range" name="mmMAX" step="1" min = "0" max = "10000000"><br>
                </b>
                <label class = "textField">Equal Population Weight:</label>
                <b>
                    <input type="number" class="range" name="eqMIN" step="0.01" min = "0" max = "1"> to
                    <input type="number" class="range" name="eqMAX" step="0.01" min = "0" max = "1"><br>
                </b>
                <label class = "textField">Compactness Weight:</label>
                <b>
                    <input type="number" class="range" name="cMIN" step="0.01" min = "0" max = "1"> to
                    <input type="number" class="range" name="cMAX" step="0.01" min = "0" max = "1"><br>
                </b>
                <label class = "textField">Partisan Fairness Weight:</label>
                <b>
                    <input type="number" class="range" name="pfMIN" step="0.01" min = "0" max = "1"> to
                    <input type="number" class="range" name="pfMAX" step="0.01" min = "0" max = "1"><br>
                </b>
                <label class = "textField">Nature Constrain Weight:</label>
                <b>
                    <input type="number" class="range" name="ncMIN"> to
                    <input type="number" class="range" name="ncMAX"><br>
                </b>
                </b>
                <label class = "textField"></label>
                <b>
                </b>
                <label class = "textField"></label>
                <b>
                </b>
                <input type = "submit" value = "submit" style = "float:right" class = "submit-btn">
            </fieldset>
        </form>
    </body>
</html>