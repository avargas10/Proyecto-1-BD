angular.module("mapSucursales", []).controller("contSuc", 
  function($scope) {


    $scope.listProducts=[
    {'Lat':9.855892, 'Lng': -83.911541},
    {'Lat':9.871465, 'Lng': -83.926277},
    {'Lat':9.852601, 'Lng': -83.933315}
    ];

    addMarker(9.871465,-83.926277);
  });



var map;

function addMarker(Lat,Lng){

  var marker = new google.maps.Marker({
    position: {lat: Lat, lng: Lng},
    map: map,
    title: 'Sucursales'
  });

}


function initMap() {
  var myLatLng = {lat: 9.855892, lng: -83.911541};

    map = new google.maps.Map(document.getElementById('map'), {
    zoom: 8,
    center: myLatLng
  });

  var marker = new google.maps.Marker({
    position: myLatLng,
    map: map,
    title: 'Sucursales'
  });

}
