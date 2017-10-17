angular.module("mainModule").controller("contEmplAdmin",["$scope","$http","userService",
function($scope,$http,userService) {
  $scope.employeelist;
  $scope. idAdmin=empresaAdmin;
  var emp;


  $scope.getHttp= function(url , callback){
    var httpObject = $http.get(url);
    httpObject.then(function(promise){
      callback(promise.data);
    }, function(error){ console.log(error);})}

    $scope.postHttp = function(url,data,callback){
      var httpObject = $http.post(url,data);
      httpObject.then(function(promise){
        callback(promise.data);
      }, function(error){ console.log(error);})}

      $scope.init = function(){
        var url = 'http://'+getIp()+':58706/api/Empleados?idSuc='+userService.getSucursal();
        $http.get(url).then(function(msg){
          emp= msg.data;
          $scope.employeelist = emp;
        });

      };


      $scope.edit=function(){
      }

      $scope.delete=function(id,nme){
        var url = 'http://'+getIp()+':58706/api/Empleados';
        var data={
          "idEmpleado":id
        };
        $http({
          method: 'DELETE',
          url: url,
          data: data,
          headers: {
              'Content-type': 'application/json;charset=utf-8'
          }
      })
      .then(function(response) {
        alert("Employee "+nme+" fired");
        $scope.init();
      }, function(rejection) {
          console.log(rejection.data);
      });
      }
      
      $scope.getRols=function(){
        var url = 'http://'+getIp()+':58706/api/Roles/'+userService.getCompany();
        $http.get(url).then(function(msg){
          $scope.rolList= msg.data;
        });
      }
      $scope.setRol=function(pRol){
          rol = pRol;
      }

      $scope.UpdateDirection = function () {
        console.log("direction update");
        var url = 'http://'+getIp()+':58706/api/Provincias';
        $http.get(url)
        .then(function successCallback(data) {
          console.log(data);
          $scope.states = data.data;
          $scope.getRols();
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
      $scope.setDirection = function (username, password, conPassword, name, surname, sSurname, id, dirSpec, date, email,admin,sucAd) {
        var url = 'http://'+getIp()+':58706/api/Direcciones';
        var sendData = {
          "Provincia": directionService.getState(),
          "Canton": directionService.getCity(),
          "Distrito": directionService.getDistrict(),
          "Descripcion": dirSpec
        };
        $scope.postHttp(url,sendData,(data)=>{
          console.log("dir: "+data.idDireccion);
         // $scope.createEmployee(username, password, conPassword, name, surname, sSurname, id, data.idDireccion, date, email,admin,sucAd);
        })
      };


    }]);