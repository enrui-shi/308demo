<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="style/style.css">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>About Page</title>
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="/script/logout.js"></script>
    <script src="/script/guest.js"></script>
</head>
<header class="topnav">
    <a onclick="window.location.replace('/')">Home</a>
    <a onclick="window.location.replace('/summary')">Summary</a>
    <a onclick="window.location.replace('/batch')">Batch</a>
    <a class="active" onclick="window.location.replace('/about')">About</a>
    <input type="image" class="user-icon" onclick="clickProfile()" src="image/user_icon.jpg" />
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
<footer class="my-footer font-small" style="background-color:#333; color:white;">
    <div class="footer-copyright text-center py-3"> © 2019 Copyright: Pirates
    </div>
</footer>
</body>
</html>
