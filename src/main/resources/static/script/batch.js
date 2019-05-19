$(document).ready(function(){
    var batch_form = $("#batch");
    batch_form.submit( function(e) {
        console.log("start to send batch data");
        document.getElementById('derr').style.display="none";
        document.getElementById('mmerr').style.display="none";
        document.getElementById('eqerr').style.display="none";
        document.getElementById('cerr').style.display="none";
        document.getElementById('pferr').style.display="none";
        document.getElementById('ncerr').style.display="none";

        if($("#dMAX").val() < $("#dMIN").val()){
              document.getElementById('derr').style.display="block";
              document.getElementById('derr').innerHTML="min number of district > max!";
              e.preventDefault();
        } else if($("#mmMAX").val() < $("#mmMIN").val()){
              document.getElementById('mmerr').style.display="block";
              document.getElementById('mmerr').innerHTML="min number of majority minority district > max!";
              e.preventDefault();
        }else if($("#eqMAX").val() < $("#eqMIN").val()){
              document.getElementById('eqerr').style.display="block";
              document.getElementById('eqerr').innerHTML="min weight of equal population > max!";
              e.preventDefault();
        }else if($("#cMAX").val() < $("#cMIN").val()){
              document.getElementById('cerr').style.display="block";
              document.getElementById('cerr').innerHTML="min weight of compactness > max!";
              e.preventDefault();
        }else if($("#pfMAX").val() < $("#pfMIN").val()){
            document.getElementById('pferr').style.display="block";
              document.getElementById('pferr').innerHTML="min weight of partisan fairness > max!";
              e.preventDefault();
        }else if($("#ncMAX").val() < $("#ncMIN").val()){
              document.getElementById('ncerr').style.display="block";
              document.getElementById('ncerr').innerHTML="min weight of natural constraint > max!";
              e.preventDefault();
        } else {

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
        }
    })
});
