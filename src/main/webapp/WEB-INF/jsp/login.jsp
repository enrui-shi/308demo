<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="style/style.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <script src="script/login.js"></script>
</head>
<body>
<div class="my-container-login" id="myForm" style="background-color:white;">
    <div class="my-wrap-login">
        <div class="my-login-logo">
            <img src="image/login.png">
        </div>
        <br>
        <form id="login" action>
            <div class="my-wrap-login-input">
                <input id="email" type = "text" name = "email" placeholder="ID" class="my-login-input"><br>
            </div>
            <div class="my-wrap-login-input">
                <input id="password" type = "password" name = "password" placeholder="Password" class="my-login-input"><br>
            </div>
            <div class="my-container-login-btn">
            <input type="submit" value="Sign In" />
            </div>
        </form>
        <br>
        <div class="text-center">Don't have an account?
            <a onclick="window.location.replace('/register')"><u>Sign Up</u></a>
        </div>
    </div>
</div>
</body>
</html>