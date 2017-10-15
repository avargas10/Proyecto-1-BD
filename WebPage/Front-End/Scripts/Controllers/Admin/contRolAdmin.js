angular.module("mainModule").controller("contRolAdmin", ["$scope","$http",
function($scope,$http) {
  $scope.rollist;
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
        var url = 'http://'+getIp()+':58706/api/Roles/{idAdmin}';
        $http.get(url).then(function(msg){
          $scope.rollist= msg.data;
        });

      };


      $scope.edit=function(){
      }
      $scope.delete=function(id,nme,desc,emp){
        var url = 'http://'+getIp()+':58706/api/Roles';
        var data={
          "idRol":id,
          "Nombre":nme,
          "Descripcion":desc,
          "idEmpresa":emp
        };
        $http.delete(url,data)
        .then(
            function(response){
              // success callback
              console.log("erase");
              animation();

            }, 
            function(response){
              // failure callback
            }
         );
      }


    }]);