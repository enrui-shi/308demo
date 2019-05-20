$(document).ready(function(){
    var batch_form = $("#batch");
    batch_form.submit( function(e) {
        console.log("start to send batch data");
        document.getElementById('derr').style.display="none";
        document.getElementById('mmerr').style.display="none";
        document.getElementById('eqerr').style.display="none";
        document.getElementById('cerr').style.display="none";
        document.getElementById('pferr').style.display="none";
        document.getElementById('lwerr').style.display="none";

        if(parseInt($("#dMAX").val()) < parseInt($("#dMIN").val())){
              document.getElementById('derr').style.display="block";
              document.getElementById('derr').innerHTML="min number of district > max!";
              e.preventDefault();
        } else if(parseInt($("#mmMAX").val()) < parseInt($("#mmMIN").val())){
              document.getElementById('mmerr').style.display="block";
              document.getElementById('mmerr').innerHTML="min number of majority minority district > max!";
              e.preventDefault();
        }else if(parseInt($("#eqMAX").val()) < parseInt($("#eqMIN").val())){
              document.getElementById('eqerr').style.display="block";
              document.getElementById('eqerr').innerHTML="min weight of equal population > max!";
              e.preventDefault();
        }else if(parseInt($("#cMAX").val()) < parseInt($("#cMIN").val())){
              document.getElementById('cerr').style.display="block";
              document.getElementById('cerr').innerHTML="min weight of compactness > max!";
              e.preventDefault();
        }else if(parseInt($("#pfMAX").val()) < parseInt($("#pfMIN").val())){
              document.getElementById('pferr').style.display="block";
              document.getElementById('pferr').innerHTML="min weight of partisan fairness > max!";
              e.preventDefault();
        }else if(parseInt($("#lwMAX").val()) < parseInt($("#lwMIN").val())){
              document.getElementById('lwerr').style.display="block";
              document.getElementById('lwerr').innerHTML="min weight of length width compactness > max!";
              e.preventDefault();
        } else {

              var d_data = {upperBound: parseInt($("#dMAX").val()), lowerBound: parseInt($("#dMIN").val())}
              var mm_data = {upperBound: $("#mmMAX").val(), lowerBound: $("#mmMIN").val()};
              var ep_data = {upperBound: $("#eqMAX").val(), lowerBound: $("#eqMIN").val()};
              var c_data = {upperBound: $("#cMAX").val(), lowerBound: $("#cMIN").val()};
              var pf_data = {upperBound: $("#pfMAX").val(), lowerBound: $("#pfMIN").val()};
              var lw_data = {upperBound: $("#lwMAX").val(), lowerBound: $("#lwMIN").val()};

              var batch_data = {numBatch: $('#num-of-batch').val(), numDistrictBound: d_data, stateName:$("input[name='state']:checked").val(),
                  numOfMMBound: mm_data, equalPopulationBound: ep_data, compactnessBound: c_data,
                  partisanFairnessBound: pf_data, lengthWidthCompactnessBound: lw_data};

              e.preventDefault();
              console.log(batch_data)

              document.getElementById('batchrun').style.display='block';



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
                      $.cookie('currentSummary', data);
                  }
              })
              var elem = document.getElementById("myBar");
              var width = 1;
              var id = setInterval(frame, 100);

              function frame() {
                  if (width >= 100) {
                    clearInterval(id);
                  } else {
                    width++;
                    elem.style.width = width + '%';
                    elem.innerHTML = width * 1  + '%';
                  }
              }

        }
    })
});
