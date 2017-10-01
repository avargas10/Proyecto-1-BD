angular.module("mapSucursales", []).controller("contSuc", 
  function($scope) {

    
  });


var map, pos;
var marker;

function initMap() {
  var myLatLng = {lat: 9.855892, lng: -83.911541};
  var sucursales=[
    [9.855892, -83.911541, 'San Jose'],
    [9.871465, -83.926277, 'Cartago'],
    [9.852601, -83.933315, 'Heredia']
    ];

  map = new google.maps.Map(document.getElementById('map'), {
    zoom: 8,
    center: myLatLng
  });

  for(var i=0; i<sucursales.length; i++){
    pos= new google.maps.LatLng(sucursales[i][0], sucursales[i][1]);
    marker = new google.maps.Marker({
      position: pos,
      map: map,
      title: 'Hello World!'
    });

  }
  
}



