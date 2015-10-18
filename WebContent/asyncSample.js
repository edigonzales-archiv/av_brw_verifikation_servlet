window.onload = setupEventHandlers;

//Counters to record the number of times the buttons were clicked.
var counter1;
var counter2;

//setting up the event handlers for the button
function setupEventHandlers() {
  counter1 = 1;
  counter2 = 1;
  var ajaxBtn1 = document.getElementById("ajaxBt1");
  var ajaxBtn2 = document.getElementById("ajaxBt2");
  ajaxBtn1.onclick = handleAjaxBtn1;
  ajaxBtn2.onclick = handleAjaxBtn2;

}

//Event handlers for the buttons

function handleAjaxBtn1(){
  var xmlhrObj = new XMLHttpRequest();
  xmlhrObj.open("GET", "http://localhost:8080/av_brw_verifikation_servlet/DemoAsyncServlet?type=1&counter="+counter1, true);
  counter1++;
  xmlhrObj.onreadystatechange = function(){
    if(xmlhrObj.readyState == 4 && xmlhrObj.status == 200){
      var div1 = document.getElementById("req1Response");
      div1.innerHTML = xmlhrObj.responseText;
    }
  };
  xmlhrObj.send();
}

function handleAjaxBtn2(){
  var xmlhrObj = new XMLHttpRequest();
  xmlhrObj.open("GET", "http://localhost:8080/av_brw_verifikation_servlet/DemoAsyncServlet?type=2&counter="+counter2, true);
  counter2++;
  xmlhrObj.onreadystatechange = function(){
    if(xmlhrObj.readyState == 4 && xmlhrObj.status == 200){
      var div2 = document.getElementById("req2Response");
      div2.innerHTML = xmlhrObj.responseText;
    }
  };
  xmlhrObj.send();
}
