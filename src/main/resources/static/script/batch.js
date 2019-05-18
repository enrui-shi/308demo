$(document).ready(function(){
    console.log('submit batch run data');
    var batch_form = $("#batch");
    batch_form.submit( function(e) {
        console.log("11111111111111")
        var d_data = {lowerBound: $("input[name='dMIN']").val(), upperBound: $("input[name='dMAX']").val()}
        var mm_data = { lowerBound: $("input[name='mmMIN']").val(), upperBound: $("input[name='mmMAX']").val()};
        var ep_data = { lowerBound: $("input[name='eqMIN']").val(), upperBound: $("input[name='eqMAX']").val()};
        var c_data = { lowerBound: $("input[name='cMIN']").val(), upperBound: $("input[name='cMAX']").val()};
        var pf_data = { lowerBound: $("input[name='pfMIN']").val(), upperBound: $("input[name='pfMAX']").val()};
        var nc_data = { lowerBound: $("input[name='ncMIN']").val(), upperBound: $("input[name='ncMAX']").val()};

        var batch_data = {stateName:$("input[name='state']:checked").val(), numBatch: $('#num-of-batch').val(),
            numDistrict: d_data, numOfMMBound: mm_data, equalPopulationBound: ep_data, compactnessBound: c_data,
            partisanFairnessBound: pf_data, natureConstrainBound: nc_data};

        e.preventDefault();
        console.log(batch_data.stateName," ",batch_data.numBatch)

        document.getElementById('batchrun').style.display='block';

        var elem = document.getElementById("myBar");
        var width = 10;

        function frame() {
            if (width >= 100) {
                clearInterval(id);
            } else {
                width++;
                elem.style.width = width + '%';
                elem.innerHTML = width * 1  + '%';
            }
        }

        console.log("start to batch");
        $.ajax({
            type: 'post',
            url: '/batch/createBatch',
            contentType: "application/json; charset=utf-8",
            header: {"accept": "application/json"},
            data: JSON.stringify(batch_data),
            dataType: "json",
            success: function (data){
                var id = setInterval(frame, 10);
                console.log("batch "+data);
            }
        })
    })
});
