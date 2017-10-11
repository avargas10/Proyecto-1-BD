angular.module("mainModule").controller("contNuevRolAdmin", ["$scope","$http",
function($scope,$http) {
  $scope.id = "";
  $scope.name="";
  $scope.desc="";

  $scope.createRol=function(nme,descr){
    var url='http://'+getIp()+':58706/api/Roles';
    var sendData={"Nombre": nme, "Descripcion":descr};

    alert(nme+descr);

    $http.post(url,sendData)
    .then(
      function successCallback(response){

      },function errorCallBack(response){

      });
    animation();
  };

}]);



