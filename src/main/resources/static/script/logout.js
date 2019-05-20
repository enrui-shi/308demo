function clickProfile() {
    document.getElementById('user01').style.display = 'block';
    // check if user login
    if($.cookie('currentuser')=="" || $.cookie('currentuser') == undefined) {
        console.log($.cookie('currentuser'));
        document.getElementById('in').style.display = 'block';
        document.getElementById('out').style.display = 'none';
    } else {
        console.log(""+$.cookie('currentuser'));
        document.getElementById('out').style.display = 'block';
        document.getElementById('in').style.display = 'none';
        document.getElementById('menubtn').style.display = 'block';
    }
}

function logout() {
    document.getElementById('log').innerHTML = "";
    // empty the cookie
    $.cookie('currentuser', "");
    $.cookie('currentSummary', "");
    $.cookie('currentStateName', "");
    $.cookie('currentScore', "");
    // back to the start page
    document.getElementById('menubtn').style.display = 'none';
    window.location.replace('/');
}