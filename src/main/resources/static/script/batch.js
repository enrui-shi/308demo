$(document).ready(function(){
    var batch_form = $("#batch");
    batch_form.submit( function(e) {
        console.log("start to send batch data");
        var d_data = {upperBound: $("#dMAX").val(), lowerBound: $("#dMIN").val()}
        var mm_data = {upperBound: $("#mmMAX").val(), lowerBound: $("#mmMIN").val()};
        var ep_data = {upperBound: $("#eqMAX").val(), lowerBound: $("#eqMIN").val()};
        var c_data = {upperBound: $("#cMAX").val(), lowerBound: $("#cMIN").val()};
        var pf_data = {upperBound: $("#pfMAX").val(), lowerBound: $("#pfMIN").val()};
        var nc_data = {upperBound: $("#ncMAX").val(), lowerBound: $("#ncMIN").val()};

        var batch_data = {numBatch: $('#num-of-batch').val(), numDistrictBound: d_data, stateName:$("input[name='state']:checked").val(),
            numOfMMBound: mm_data, equalPopulationBound: ep_data, compactnessBound: c_data,
            partisanFairnessBound: pf_data, natureConstrainBound: nc_data};

        e.preventDefault();
        console.log(batch_data)

        document.getElementById('batchrun').style.display='block';

        /*var elem = document.getElementById("myBar");
        var width = 10;

        function frame() {
            if (width >= 100) {
                clearInterval(id);
            } else {
                width++;
                elem.style.width = width + '%';
                elem.innerHTML = width * 1  + '%';
            }
        }*/

        console.log("start to batch");
        $.ajax({
            type: 'post',
            url: '/runbatch/creatPiratesBatch',
            contentType: "application/json; charset=utf-8",
            header: {"accept": "application/json"},
            data: JSON.stringify(batch_data),
            dataType: "json",
            success: function (data){
                //var id = setInterval(frame, 10);
                console.log("batch "+data);
            }
        })
    })
});
