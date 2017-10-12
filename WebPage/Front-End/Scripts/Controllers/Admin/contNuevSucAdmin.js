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

    $scope.postHttp = function (url, data, callback) {
      var httpObject = $http.post(url, data);
      httpObject.then(function (promise) {
        callback(promise.data);
      }, function (error) { console.log(error); })
    }

    $scope.updateInfo=function(username, password, conPassword, name, surname, sSurname, id, dir, date, email){
      console.log("username "+username);
      console.log("name "+name);
      console.log("surname "+surname);
      console.log("email "+email);
    }


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


    $scope.createStore=function(idE,nme,idC,idP,idD,dr,lt,ln){
      idE=parseInt(idE.split("",1));
      idC=parseInt(idC.split("",1));
      idP=parseInt(idP.split("",1));
      idD=parseInt(idD.split("",1));
      lt=parseFloat(lt);
      ln=parseFloat(ln);

      alert(nme);
      alert(dr);

      var url='http://'+getIp()+':58706/api/Sucursal';
      var sendData={
        "idEmpresa": idE,
        "idProvincia": idP,
        "idCanton":idC, 
        "idDistrito":idD,
        "Latitud":lt,
        "Longitud":ln,
        "detalleDireccion":dr,
        "Nombre":nme ,
        "Estado":1
      };
      
      $scope.postHttp(url,sendData,(data)=>{
          if(data){

          }
        });

        
      animation();
    }

  }]);