angular.module("mainModule").controller("contNuevSucAdmin", ["$scope","$http","directionService",'$location',
'storeService','userService',

  function($scope,$http,directionService,$location,storeService,userService) {
    $scope.idEmp = "";
    $scope.name="";
    $scope.idCant="";
    $scope.idProv="";
    $scope.idDist="";
    $scope.dir="";
    $scope.lat="";
    $scope.lng="";

    $scope.empList;
    $scope.provList;
    $scope.cantList;
    $scope.distList;

    $scope.idAdmin=empresaAdmin;


    $scope.init = function(){
      var url = 'http://'+getIp()+':58706/api/Distrito';
      $http.get(url).then(function(msg){
        $scope.distList = msg.data;
        
      });
      url = 'http://'+getIp()+':58706/api/Cantones';
      $http.get(url).then(function(msg){
        $scope.cantList = msg.data;
      });
      url = 'http://'+getIp()+':58706/api/Empresa';
      $http.get(url).then(function(msg){
        $scope.empList = msg.data;
      });
      url = 'http://'+getIp()+':58706/api/Provincias';
      $http.get(url).then(function(msg){
        $scope.provList = msg.data;
      });

    }


    $scope.createStore=function(nme,dr){
      var url='http://'+getIp()+':58706/api/Sucursal';
      var sendData={"Nombre": nme, "detalleDireccion":dr, "idEmpresa":userService.getCompany(), "idCanton":directionService.getCity(), "idProvincia": directionService.getState(), "idDistrito":directionService.getDistrict() , "Estado":"1"};
      console.log("send: "+sendData);
      $scope.postHttp(url,sendData,(data)=>{
        console.log("id: "+data.idSucursal);
        storeService.setID(data.idSucursal);
      })
    }

  }]);