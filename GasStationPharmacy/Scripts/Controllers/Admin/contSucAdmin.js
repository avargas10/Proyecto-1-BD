angular.module("mainModule").controller("contSucAdmin", ["$scope","$http","$location"
,"userService","directionService",
function($scope,$http,$location,userService,directionService) {
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
        var url = 'http://'+getIp()+':58706/api/Sucursal?empresa='+userService.getCompany();
        $http.get(url).then(function(msg){
          $scope.sucList= msg.data;
        });

        
      };

    

      $scope.delete=function(id){
        var url = 'http://'+getIp()+':58706/api/Sucursal';
        var data={
          "idSucursal":id
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
        alert("Store "+id+" deleted");
        $scope.init();
      }, function(rejection) {
          console.log(rejection.data);
      });
      
    }


  $scope.edit=function(ID,dir,nme){
        var url = 'http://'+getIp()+':58706/api/Sucursal';
        var data={
          "idSucursal":parseInt(ID),
          "idEmpresa":userService.getCompany(),
          "idProvincia": directionService.getState(),
          "idCanton":directionService.getCity(),
          "idDistrito":directionService.getDistrict(),
          "detalleDireccion":dir,
          "Nombre":nme,
          "Estado":1,
          "Imagen":""
        };
        console.log("dir "+dir);
        $http.put(url,data)
        .then(
            function(response){
              // success callback
              console.log("update");
              $scope.init();
              

            }, 
            function(response){
              // failure callback
            }
         );


  }

}]);
