angular.module("mainModule").controller("contMedAdmin",["$scope","$http",
function($scope,$http) {
  $scope.medList;
  $scope.idAdmin=empresaAdmin;
  var client;
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
        var url = 'http://localhost:58706/api/Productos';
        $http.get(url).then(function(msg){
          client = msg.data;
          $scope.medList = client;
        });

      }

      

      $scope.edit=function(name,id){
      }

      $scope.delete=function(){
        this.medList.pop();
        animation();
      }

    //this.getProducts();


    }]);


