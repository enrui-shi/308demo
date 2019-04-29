$(document).ready(function(){
    console.log('submit batch run data');
    var batch_form = $("#batch");
    batch_form.submit( function(e) {
        var preferenceBound = {}
        var batch_data = {stateName: $("input[name='state']:checked").val(), numBatch: $('#num-of-batch').val() ,
            mmMIN: $("input[name='mmMIN']").val(), mmMAX: $("input[name='mmMAX']").val(), eqMIN: $("input[name='eqMIN']").val(),
            eqMAX: $("input[name='eqMAX']").val(), cMIN: $("input[name='cMIN']").val(), cMAX: $("input[name='cMAX']").val(),
            pfMIN: $("input[name='pfMIN']").val(), pfMAX: $("input[name='pfMAX']").val(),ncMIN: $("input[name='ncMIN']").val(),
            ncMAX: $("input[name='ncMAX']").val()};
        e.preventDefault();
        console.log(batch_data.state," ",batch_data.numBatch)
        $.ajax({
            type: 'post',
            url: '/home/batch',
            contentType:"application/json; charset=utf-8",
            data: JSON.stringify(batch_data),
            dataType:"json",
            success: function (data){
                console.log(data);
                window.location.replace("/home/main");

            }
        })
    })
});