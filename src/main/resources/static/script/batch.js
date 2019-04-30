$(document).ready(function(){
    console.log('submit batch run data');
    var batch_form = $("#batch");
    batch_form.submit( function(e) {
        console.log("11111111111111")
        var batch_data = {};
        batch_data[stateName] = $("input[name='state']:checked").val();
        batch_data[numBatch] = $('#num-of-batch').val();
        batch_data[numDistrict] = $('#num-of-district').val();
        batch_data[numOfMMBound] = { mmMIN: $("input[name='mmMIN']").val(), mmMAX: $("input[name='mmMAX']").val()};
        batch_data[equalPopulationBound] = { eqMIN: $("input[name='eqMIN']").val(), eqMAX: $("input[name='eqMAX']").val()};
        batch_data[compactnessBound] = { cMIN: $("input[name='cMIN']").val(), cMAX: $("input[name='cMAX']").val()};
        batch_data[partisanFairnessBound] = { pfMIN: $("input[name='pfMIN']").val(), pfMAX: $("input[name='pfMAX']").val()};
        batch_data[natureConstrainBound] = { ncMIN: $("input[name='ncMIN']").val(), ncMAX: $("input[name='ncMAX']").val()};

        e.preventDefault();
        console.log(batch_data.state," ",batch_data.numBatch)
        $.ajax({
            type: 'post',
            url: '/batch/createBatch',
            header: {"accept": "application/json"},
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