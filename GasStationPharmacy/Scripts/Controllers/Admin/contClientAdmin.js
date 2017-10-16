angular.module("mainModule").controller("contClientAdmin",["$scope","$http",
function($scope,$http) {
  $scope.clientlist;
  var client;
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
        var url = 'http://'+getIp()+':58706/api/Clientes';
        $http.get(url).then(function(msg){
          client= msg.data;
          $scope.clientlist = client;
        });

      };


      $scope.edit=function(id,dir,nme,pAp,sAp,pass,usr,ste,pnz,date){

        var url = 'http://'+getIp()+':58706/api/Clientes';
        var data={
          "Cedula":id,
          "Direccion":dir,
          "Nombre":nme,
          "pApellido":pAp,
          "sApellido":sAp,
          "Password":pass,
          "Username":usr,
          "Estado":ste,
          "Penalizacion":pnz,
          "Nacimiento":date

        };
        $http.put(url,data)
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


      $scope.delete=function(id,dir,nme,pAP,sAp,pass,usr,ste,pnz,data){

        var url = 'http://'+getIp()+':58706/api/Clientes';
        var data={
          "Cedula":id,
          "Direccion":dir,
          "Nombre":nme,
          "pApellido":pAp,
          "sApellido":sAp,
          "Password":pass,
          "Username":usr,
          "Estado":ste,
          "Penalizacion":pnz,
          "Nacimiento":date

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



