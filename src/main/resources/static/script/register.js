$(document).ready(function(){
    var register_form = $("#register");
    register_form.submit( function(e) {
        var register_data = { userEmail: $('#email').val() , password: $('#password').val() };
        e.preventDefault();
        console.log(register_data.userEmail," ",register_data.password)
        $.ajax({
            type: 'post',
            url: '/user/register',
            headers: {
                Accept: "*/*"
            },
            contentType:"application/json; charset=utf-8",
            data: JSON.stringify(register_data),
            dataType:"json",
            success: function (data){
                console.log(data);
                if(data.status == 'error') {
                    document.getElementById('error').style.display="block";
                    document.getElementById('error').innerHTML="Email exists!";
                } else {
                    document.getElementById('error').style.display="none";
                    $.cookie('currentuser', register_data.userEmail);
                    window.location.replace("/");
                }
            }
        })
    })
});