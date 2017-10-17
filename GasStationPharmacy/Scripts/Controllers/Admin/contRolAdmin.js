angular.module("mainModule").controller("contRolAdmin", ["$scope","$http",'userService',
function($scope,$http,userService) {
  $scope.rolList=[
    {"Nombre":"Cajero", "idRol":"1","Descripcion":"Caja","idEmpresa":"1"},
    {"Nombre":"Cajero", "idRol":"1","Descripcion":"Caja","idEmpresa":"1"},
    {"Nombre":"Cajero", "idRol":"1","Descripcion":"Caja","idEmpresa":"1"},
    {"Nombre":"Cajero", "idRol":"1","Descripcion":"Caja","idEmpresa":"1"}

  ];
  $scope.idAdmin=empresaAdmin;
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
        var url = 'http://'+getIp()+':58706/api/Roles/'+userService.getCompany();
        $http.get(url).then(function(msg){
          $scope.rolList= msg.data;
        });

      };




      $scope.edit=function(id,name,desc){
        var url = 'http://'+getIp()+':58706/api/Roles';
        var sendData= {
        "idRol": parseInt(id),
        "Nombre": name,
        "Descripcion": desc,
        "Estado": 1,
        "Empresa": userService.getCompany()
      };
      $http.put(url,sendData)
      .then(
          function(response){
            // success callback
            console.log(" Rol Update");
            $scope.init();
          }, 
          function(response){
            // failure callback
          }
       );
      }
      $scope.delete=function(id,nme,desc){
        var url = 'http://'+getIp()+':58706/api/Roles';
        var data={
          "idRol": id,
          "Nombre":nme,
          "Descripcion":desc,
          "idEmpresa":userService.getCompany()
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
          alert("Rol "+nme+" deleted");
          $scope.init();
        }, function(rejection) {
            console.log(rejection.data);
        });
        
      
      }
    }]);