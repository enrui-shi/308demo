$(document).ready(function(){

    $.ajax({
        type: 'post',
        url: '/admin/getusers',
        contentType: "application/json; charset=utf-8",
        headers: {
            Accept: "application/json; charset=utf-8"
        },
        success: function (data) {
            console.log(data)
            showUser(data)
            $(".delete").click(function(){
                console.log("delete user:"+$(this).attr("value"))
                d_data= {'id':$(this).attr("value")}
                $.ajax({
                    type: 'post',
                    url: '/admin/deleteUser',
                    contentType: "application/json; charset=utf-8",
                    headers: {
                        Accept: "application/json; charset=utf-8"
                    },
                    data: JSON.stringify(d_data),
                    dataType:"json",
                    success: function (data) {
                        console.log(data)
                        window.location.replace("/admin");
                    }
                });
            });

        }
    });

});




function showUser(json){
    $("#users").empty();
    var ul = '<ul>';
    for(var i=0;i<json.length;i++){
        console.log(json[i]);
        ul +=  '<li>'+'<h4>'+json[i].userEmail+'</h4>'+
            '<button class=delete value='+json[i].id+'>DELETE</button>'+'</li>';
        //$(document.createElement('li')).text("title: " +json[i].title +"\n"+"body: "+json[i].body)
    }
    ul += "</ul>";
    $("#users").append(ul);
}
