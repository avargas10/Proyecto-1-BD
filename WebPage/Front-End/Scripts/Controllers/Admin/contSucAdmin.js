angular.module("mainModule").controller("contSucAdmin", ["$scope","$http",
function($scope,$http) {

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
        var url = 'http://'+getIp()+':58706/api/Sucursal';
        $http.get(url).then(function(msg){
          $scope.sucList= msg.data;
        });

      };

      $scope.delete=function(id){
        var url = 'http://'+getIp()+':58706/api/Sucursal?idSucursal='+id;
        $http.put(url)
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


  $scope.edit=function(name,id){
  }

}]);

