<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" type="text/css" href="style/batchstyle.css">
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
        <script src="/script/batch.js"></script>
        <script src="/script/logout.js"></script>
        <script src="/script/guest.js"></script>
        <title>BatchRun</title>
    </head>
    <header>
        <div class="topnav">
            <a onclick="window.location.replace('/')">Home</a>
            <a onclick="window.location.replace('/summary')">Summary</a>
            <a class="active" onclick="window.location.replace('/batch')">Batch</a>
            <a onclick="window.location.replace('/about')">About</a>
            <input type="image" class="user-icon" onclick="clickProfile()" src="image/user_icon.jpg" />
        </div>
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

      <form id="batch">
          <h1>Batch Run</h1>
          <label class = "textField"> STATE:</label>
          <b>
              NY <input type="radio" name="state" value="NY">
              IA <input type="radio" name="state" value="IA">
              OH <input type="radio" name="state" value="OH"><br>
          </b>
          <label class = "textField"> Number of batch:</label>
          <b> <input id="num-of-batch" class="inputNum" type="number" step="1"> <br></b>
          <label class = "textField"> Number of district:</label>
          <b>
              <input type="number" class="range" id="dMIN" step="1" min = "0" max = "10000"> to
              <input type="number" class="range" id="dMAX" step="1" min = "0" max = "10000000"><br>
          </b>
          <label class = "textField">Number of majority minority:</label>
          <b>
              <input type="number" class="range" id="mmMIN" step="1" min = "0" max = "10000"> to
              <input type="number" class="range" id="mmMAX" step="1" min = "0" max = "10000000"><br>
          </b>
          <label class = "textField">Equal Population Weight:</label>
          <b>
              <input type="number" class="range" id="eqMIN" step="0.01" min = "0" max = "1"> to
              <input type="number" class="range" id="eqMAX" step="0.01" min = "0" max = "1"><br>
          </b>
          <label class = "textField">Compactness Weight:</label>
          <b>
              <input type="number" class="range" id="cMIN" step="0.01" min = "0" max = "1"> to
              <input type="number" class="range" id="cMAX" step="0.01" min = "0" max = "1"><br>
          </b>
          <label class = "textField">Partisan Fairness Weight:</label>
          <b>
              <input type="number" class="range" id="pfMIN" step="0.01" min = "0" max = "1"> to
              <input type="number" class="range" id="pfMAX" step="0.01" min = "0" max = "1"><br>
          </b>
          <label class = "textField">Nature Constrain Weight:</label>
          <b>
              <input type="number" class="range" id="ncMIN" step="0.01" min = "0" max = "1"> to
              <input type="number" class="range" id="ncMAX" step="0.01" min = "0" max = "1"><br>
          </b>
          </b>
          <label class = "textField"></label>
          <b>
          </b>
          <label class = "textField"></label>
          <b>
          </b>
          <input type = "submit" value = "submit" style = "float:right" class = "submit-btn">
      </form>

      <div id="batchrun" class="my-batch-modal">
          <div class="my-batch-modal-content w3-animate-zoom ">
              <span onclick="document.getElementById('batchrun').style.display='none'" class="close w3-button w3-small w3-hover-red display-topright" title="Close Modal">&times;</span>
              <br>
              <br>
              <div id="myProgress">
                  <div id="myBar">10%</div>
              </div>
              <br>
              <div>
                <span><a>See batch run summaries</a></span>
              </div>
          </div>
      </div>
    </body>
</html>
