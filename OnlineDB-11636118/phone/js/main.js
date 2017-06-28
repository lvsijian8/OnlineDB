
function zy_touch(c, f){
    var t = event.currentTarget;
    if(!t.zTouch) {
        t.zTouch = new zyClick(t, f, c); 
        t.zTouch._touchStart(event);
    }
}


/*************弹窗***************/
$(document).ready(function() {
   $("#sel").click(function() {
    $("#dialog").css({
     "display": "block",
     "opacity": "0.3",
     "top": "50%"
    })
    $("#dialog").animate({
     "opacity": "1",
     "top": "15%"
    }, 300);
    $("#main").css("-webkit-filter","blur(10px)");
   })
   $("#close").click(function() {
    $("#dialog").animate({
     opacity: "0",
     top: "20%"
    }, 200, colseDialog);
    $("#main").css("-webkit-filter","blur(0px)");
   })
  })
  function colseDialog() {
   $("#dialog").css({
    "display": "none",
    "opacity": "0.3",
    "top": "50%"
   })
  }

/***************checkbox****************/
  
  $("#ckAll").click(function() {
    $("input[name='sub']").prop("checked", this.checked);
  });
  
  $("input[name='sub']").click(function() {
    var $subs = $("input[name='sub']");
    $("#ckAll").prop("checked" , $subs.length == $subs.filter(":checked").length ? true :false);
  });
 
/****************添加和删除**********************/

        $("#add").click(function() {
            var fir = $('tr:last').clone();
            $('tr:last').after(fir);      
            // var trCnt = table.rows.length; 
            // var tdCnt = table.rows[0].cells.length;  
            // var row=document.getElementById('tbcontainer').insertRow(0);
            // var cell1=row.insertCell(0);
            // cell1.innerHTML="<input type="checkbox" name="sub" id="" value="" />";
            // for (var i=0; i<tdCnt-1; i++){
                  // row.insertCell(i);
            // }
        });
        
        $("#del").click(function() {
            $("input[name='sub']:checked").each(function() {
                n = $(this).parents("tr").index(); 
                $("table#tbcontainer").find("tr:eq("+n+")").remove();
            });
        });
        

/******************表格拖拽改宽度***************/

$(function(){
  $("table").colResizable();
});