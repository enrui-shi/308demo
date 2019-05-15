$(document).ready(function(){
    console.log('submit batch run data');
    var batch_form = $("#batch");
    batch_form.submit( function(e) {
        console.log("11111111111111")
        var mm_data = { mmMIN: $("input[name='mmMIN']").val(), mmMAX: $("input[name='mmMAX']").val()};
        var ep_data = { eqMIN: $("input[name='eqMIN']").val(), eqMAX: $("input[name='eqMAX']").val()};
        var c_data = { cMIN: $("input[name='cMIN']").val(), cMAX: $("input[name='cMAX']").val()};
        var pf_data = { pfMIN: $("input[name='pfMIN']").val(), pfMAX: $("input[name='pfMAX']").val()};
        var nc_data = { ncMIN: $("input[name='ncMIN']").val(), ncMAX: $("input[name='ncMAX']").val()};

        var batch_data = {stateName:$("input[name='state']:checked").val(), numBatch: $('#num-of-batch').val(),
            numDistrict: $('#num-of-district').val(), numOfMMBound: mm_data, equalPopulationBound: ep_data, compactnessBound: c_data,
            partisanFairnessBound: pf_data, natureConstrainBound: nc_data};

        e.preventDefault();
        console.log(batch_data.stateName," ",batch_data.numBatch)

        /*function startBatch() {
            var elem = document.getElementById("myBar");
            var width = 1;
            var id = setInterval(frame, 100000);
            function frame() {
                if (width >= 100) {
                    clearInterval(id);
                } else {
                    width++;
                    elem.style.width = width + '%';
                    elem.innerHTML = width * 1  + '%';
                }
            }
        }*/

        $.ajax({
            type: 'post',
            url: '/batch/createBatch',
            header: {"accept": "application/json"},
            contentType:"application/json; charset=utf-8",
            data: JSON.stringify(batch_data),
            dataType:"json",
            success: function (data){
                console.log(data);
            }
        })
    })
});