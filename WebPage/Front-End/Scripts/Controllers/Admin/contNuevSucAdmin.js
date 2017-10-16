angular.module("mainModule").controller("contNuevSucAdmin", ["$scope","$http","$location",

  function($scope,$http,$location) {
    $scope.idEmp = "";
    $scope.name="";
    $scope.idCant="";
    $scope.idProv="";
    $scope.idDist="";
    $scope.dir="";

    $scope.adminId;
    $scope.nameAdmin;
    $scope.emailAdmin;
    $scope.adminUser;
    $scope.adminPass;
    $scope.adminS;
    $scope.adminSS;
    $scope.adminDir;
    $scope.adminDate;

    $scope.idSucAdmin;


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


    $scope.createStore=function(idE,nme,idC,idP,idD,dr){
      idE=parseInt(idE.split("",1));
      idC=parseInt(idC.split("",1));
      idP=parseInt(idP.split("",1));
      idD=parseInt(idD.split("",1));

      var url='http://'+getIp()+':58706/api/Sucursal';
      var sendData={
        "idEmpresa": idE,
        "idProvincia": idP,
        "idCanton":idC, 
        "idDistrito":idD,
        "detalleDireccion":dr,
        "Nombre":nme,
        "Estado":1
      };
    
      $scope.postHttp(url,sendData,(data)=>{
          if(data){
            this.idSucAdmin=data.idSucursal;
          }
        });
      animation();
    }

    $scope.createAdmin=function(aId,nAd,emAd,adUsr,adPass,adS,adSS,adDir,adDate){
      alert(adDate);
      aId=parseInt(aId);
      var data={
        "idRol": aId,
        "idSucursal":idSucAdmin,
        "Nombre":nAd,
        "Email":emAd,
        "Username": adUsr,
        "Passwaord": adPass,
        "pApellido":adS,
        "sApellido":adSS,
        "detalleDireccion": adDir,
        "Nacimientos":adDate,
        "Estado":1
//idRol, toda la info, idSucursal, estado 1
      };
      
      var url='http://'+getIp()+':58706/api/Empleados';
      $scope.postHttp(url,sendData,(data)=>{
          if(data){
          }
        });
    }







 $scope.setDirection = function (id,mail,user,pass,nme,pAp,sAp,date,dirSpec) {
        var url = 'http://'+getIp()+':58706/api/Direcciones';
        var sendData = {
          "Provincia": directionService.getState(),
          "Canton": directionService.getCity(),
          "Distrito": directionService.getDistrict(),
          "Descripcion": dirSpec
        };
        $scope.postHttp(url,sendData,(data)=>{
          console.log("dir: "+data.idDireccion);
          $scope.createUser(username, password, conPassword, name, surname, sSurname, id, data.idDireccion, date, email);
        })
      };





      $scope.UpdateDirection = function () {
        console.log("direction update");
        var url = 'http://'+getIp()+':58706/api/Provincias';
        $http.get(url)
        .then(function successCallback(data) {
          console.log(data);
          $scope.states = data.data;
        },
        function errorCallback(response) {
          alert(response);
        });
      };
      $scope.UpdateCities = function (_id) {
        directionService.setState(_id);
        var url = 'http://'+getIp()+':58706/api/Cantones?idProvincia=' + _id;
        $http.get(url)
        .then(function successCallback(data) {
          console.log(data);
          $scope.cities = data.data;
        },
        function errorCallback(response) {
          alert(response);
        });
      };
      $scope.UpdateDistricts = function (_id) {
        directionService.setCity(_id);
        var url = 'http://'+getIp()+':58706/api/Distrito?idCanton=' + _id;
        $http.get(url)
        .then(function successCallback(data) {
          console.log(data);
          $scope.districts = data.data;
        },
        function errorCallback(response) {
          alert(response);
        });
      };
      $scope.setDistrict = function (_id) {
        directionService.setDisctrict(_id);
      };








  }]);