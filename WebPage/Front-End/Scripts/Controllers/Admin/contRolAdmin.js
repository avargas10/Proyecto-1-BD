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
        var url = 'http://192.168.43.110:58706/api/Roles';
        $http.get(url).then(function(msg){
          $scope.rollist= msg.data;
        });

      };


      $scope.edit=function(){
      }
      $scope.delete=function(){
        this.rollist.pop();
        animation();
      }

    }]);