angular.module("mainModule").controller("contSucAdmin", ["$scope","$http",
function($scope,$http) {
  $scope.sucList;

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



  $scope.edit=function(name,id){
  }
  $scope.delete=function(){
      this.sucList.pop();
      animation();
  }

}]);

