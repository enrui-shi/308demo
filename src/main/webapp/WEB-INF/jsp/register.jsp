<html>
    <head>
        <link rel="stylesheet" href="style/style.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="/script/register.js"></script>
        <script src="/script/guest.js"></script>
    </head>
    <body>
        <div class="my-container-login" style="background-color:white;">
            <div class="my-wrap-login">
                <div class="my-login-logo">
                    <img src="image/sign_up.png">
                </div>
                <form id="register">
                    <div class="my-wrap-login-input">
                        <input id="email" type = "text" name = "email" placeholder="email" class="my-login-input" required><br>
                    </div>
                    <div class="my-wrap-login-input">
                        <input id="password" type = "password" name = "password" placeholder="Password" class="my-login-input" required><br>
                    </div>
                    <div class="my-container-login-btn">
                        <input class="my-login-btn" type="submit" value="Sign Up" />
                    </div>
                </form>
                <div class="my-container-login-btn">
                    <button onclick="window.location.replace('/login')" class="my-login-btn">Login </button>
                </div>
            </div>
        </div>
    </body>
</html>
