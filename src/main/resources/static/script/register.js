$(document).ready(function(){
    var register_form = $("#register");
    register_form.submit( function(e) {
        var register_data = { userEmail: $('#email').val() , password: $('#password').val() };
        e.preventDefault();
        console.log(register_data.userEmail," ",register_data.password)
        $.ajax({
            type: 'post',
            url: '/user/register',
            contentType:"application/json; charset=utf-8",
            data: JSON.stringify(register_data),
            dataType:"json",
            success: function (data){
                console.log(data);
                window.location.replace("/");

            }
        })
    })
});