function clickProfile() {
    document.getElementById('user01').style.display = 'block';
    // check if user login
    if($.cookie('currentuser')=="") {
        document.getElementById('in').style.display = 'block';
        document.getElementById('out').style.display = 'none';
    } else {
        document.getElementById('out').style.display = 'block';
        document.getElementById('in').style.display = 'none';
    }
}

function logout() {
    // empty the cookie
    $.cookie('currentuser', "");
    // back to the start page
    window.location.replace('/');
}