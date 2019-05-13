$(document).ready(function(){
    var login_form = $("#login");
    login_form.submit( function(e) {
        var login_data = { userEmail: $('#email').val(), password: $('#password').val() };
        console.log(login_data.userEmail," ",login_data.password)
        e.preventDefault();
        $.ajax({
            type: 'post',
            url: '/user/login',
            contentType:"application/json; charset=utf-8",
            headers: {
                Accept: "application/json; charset=utf-8"
            },
            data: JSON.stringify(login_data),
            dataType:"json",
            success: function (data){
                if(data.status == 'error'){
                    if(data.error == 'cannot find user') {
                        document.getElementById('psderror').style.display="none";
                        document.getElementById('emailerror').style.display="block";
                        document.getElementById('emailerror').innerHTML="Can't find this email!";
                    } else {
                        document.getElementById('emailerror').style.display="none";
                        document.getElementById('psderror').style.display="block";
                        document.getElementById('psderror').innerHTML="Password is incorrect!";
                    }
                } else {
                    document.getElementById('emailerror').style.display="none";
                    document.getElementById('psderror').style.display="none";
                    // set up the current user
                    $.cookie('currentuser', $('#email').val());
                    // jump to the main page
                    window.location.replace("/");
                    console.log(data);
                }
            }
        })
    })
});