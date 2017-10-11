var globalImage="";
var empresaAdmin;
var app = angular.module("mainModule",
  ["ngRoute",
    'vcRecaptcha']);

var openFile = function(event) {
    var input = event.target;
    var reader = new FileReader();
    reader.onload = function(){
      var dataURL = reader.result;
      var output = document.getElementById('output');
      output.src = dataURL;
      globalImage = dataURL;
      
    };
    reader.readAsDataURL(input.files[0]);
 }
  

 function newEmploy(){
  alert('Nuevo empleado');
  window.location.href="../Views/nuevEmpleado.html";
}

function newRol(){
  window.location.href="../Views/nuevRol.html";
}

function newSuc(){
  window.location.href = "../Views/nuevSuc.html";
}

function newProd(){
  window.location.href = "../Views/nuevMed.html";
  }


  var productos=[
    ["Talerdin",200],
    ["Tabcin",450],
    ["Antifludes",100],
    ["Condones",1000],
    ["Suero",20]
    ];
    
    
    
    //Vista estadistica
    
    var chart = AmCharts.makeChart("chartdiv", {
      "type": "serial",
      "theme": "light",
      "marginRight": 70,
      "dataProvider": [{
        "country": productos[0][0],
        "visits": productos[0][1],
        "color": "#FF0F00"
      }, {
        "country": productos[1][0],
        "visits": productos[1][1],
        "color": "#FF6600"
      }, {
        "country": productos[2][0],
        "visits": productos[2][1],
        "color": "#FF9E01"
      }, {
        "country": productos[3][0],
        "visits": productos[3][1],
        "color": "#FCD202"
      }, {
        "country": productos[4][0],
        "visits": productos[4][1],
        "color": "#F8FF01"
      }],
      "valueAxes": [{
        "axisAlpha": 0,
        "position": "left",
        "title": "Quantity"
      }],
      "startDuration": 1,
      "graphs": [{
        "balloonText": "<b>[[category]]: [[value]]</b>",
        "fillColorsField": "color",
        "fillAlphas": 0.9,
        "lineAlpha": 0.2,
        "type": "column",
        "valueField": "visits"
      }],
      "chartCursor": {
        "categoryBalloonEnabled": false,
        "cursorAlpha": 0,
        "zoomable": false
      },
      "categoryField": "country",
      "categoryAxis": {
        "gridPosition": "start",
        "labelRotation": 45
      },
      "export": {
        "enabled": true
      }
    
    });
    
    
    
    
    
    
    var chart = AmCharts.makeChart( "chartdivr", {
      "type": "pie",
      "theme": "light",
      "dataProvider": [ {
        "company": "BombaTica",
        "sell": 5000
      }, {
        "company": "Phischell",
        "sell": 3590
      }],
      "valueField": "sell",
      "titleField": "company",
       "balloon":{
       "fixedPosition":true
      },
      "export": {
        "enabled": true
      }
    } );
    
    
      function isBlank(str) {
        return (!str || /^\s*$/.test(str));
      }
    
    function animation() {
        var x = document.getElementById("snackbar")
        x.className = "show";
        setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
    }