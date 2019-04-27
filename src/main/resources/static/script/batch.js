$(document).ready(function(){
    var register_form = $("#batch");
    register_form.submit( function(e) {
        var batch_data = {state: $("input[name='state']:checked").val(), numBatch: $('#num-of-batch').val() ,
            mmMIN: $("input[name='mmMIN']").val(), mmMAX: $("input[name='mmMAX']").val(), eqMIN: $("input[name='eqMIN']").val(),
            eqMAX: $("input[name='eqMAX']").val(), cMIN: $("input[name='cMIN']").val(), cMAX: $("input[name='cMAX']").val(),
            pfMIN: $("input[name='pfMIN']").val(), pfMAX: $("input[name='pfMAX']").val(),ncMIN: $("input[name='ncMIN']").val(),
            ncMAX: $("input[name='ncMAX']").val()};
        e.preventDefault();
        //console.log(batch_data.state," ",batch_data.numBatch)
        $.ajax({
            type: 'post',
            url: '/user/batch',
            contentType:"application/json; charset=utf-8",
            data: JSON.stringify(batch_data),
            dataType:"json",
            success: function (data){
                console.log(data);
                window.location.replace("/main");

            }
        })
    })
});