angular.module("mainModule").controller("contNuevSucAdmin", ["$scope","$http",

  function($scope,$http) {
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
      var url = 'http://192.168.43.110:58706/api/Distrito';
      $http.get(url).then(function(msg){
        $scope.distList = msg.data;
        
      });
      url = 'http://192.168.43.110:58706/api/Cantones';
      $http.get(url).then(function(msg){
        $scope.cantList = msg.data;
      });
      url = 'http://192.168.43.110:58706/api/Empresa';
      $http.get(url).then(function(msg){
        $scope.empList = msg.data;
      });
      url = 'http://192.168.43.110:58706/api/Provincias';
      $http.get(url).then(function(msg){
        $scope.provList = msg.data;
      });

    }


    $scope.createStore=function(idE,nme,idC,idP,idD,dr,lt,ln){
      idE=idE.split("",1);
      idC=idC.split("",1);
      idP=idP.split("",1);
      idD=idD.split("",1);
      var url='http://192.168.43.110:58706/api/Sucursal';
      var sendData={"Nombre": nme, "detalleDireccion":dr, "idEmpresa":idE, "idCanton":idC, "idProvincia": idP, "idDistrito":idD, "Latitud": lt, "Longitud": ln , "Estado":"1"};
      
      $http.post(url,sendData)
      .then(
        function successCallback(response){

        },function errorCallBack(response){

        });
        
      animation();
    }

  }]);