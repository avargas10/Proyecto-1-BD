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


  $scope.edit=function(){
    alert('hola');
    alert(this.idAdmin);
  }

  $scope.delete=function(){
      this.clientlist.pop();
      animation();
  }

}]);



