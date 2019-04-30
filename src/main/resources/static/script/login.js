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
            data: JSON.stringify(login_data),
            dataType:"json",
            success: function (data){
                if(data == null){
                    alert("Password or email is incorrect");
                } else {
                    window.location.replace("/main");
                    console.log(data);
                }
            }
        })
    })
});