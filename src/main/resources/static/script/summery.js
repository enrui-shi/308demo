$(document).ready(function(){
    var summery = $.cookie('currentSummary')
    showSummery(summery)

});


function showSummery(json){
    $("#summery").empty();

    console.log(json)
    var ul = '<ul>';
    for(var i=0;i<json.length;i++){
        console.log(json[i]);

        ul +=  '<li>'+'<p>'+json[i]+'</p>'+'</li>';
        //$(document.createElement('li')).text("title: " +json[i].title +"\n"+"body: "+json[i].body)
    }
    ul += "</ul>";
    $("#users").append(ul);
}