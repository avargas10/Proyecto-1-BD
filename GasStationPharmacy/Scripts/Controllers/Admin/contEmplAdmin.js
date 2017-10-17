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

    }]);