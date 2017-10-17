angular.module("mainModule").controller("contNuevRolAdmin", ["$scope","$http","userService",'$location',
function($scope,$http,userService,$location) {
  $scope.id = "";
  $scope.name="";
  $scope.desc="";

  $scope.createRol=function(nme,descr){
    console.log('empresa: '+userService.getCompany());
    var url='http://'+getIp()+':58706/api/Roles';
    var sendData={
    "Nombre": nme, 
    "Descripcion":descr,
    "Empresa":userService.getCompany(),
    "Estado":1
  };
    $http.post(url,sendData)
    .then(
      function successCallback(response){
        alert("Rol: "+nme+" created");
        $location.path('/Admin/groles');
      },function errorCallBack(response){

      });
  };

      $scope.init = function(){
      url = 'http://'+getIp()+':58706/api/Empresa';
      $http.get(url).then(function(msg){
        $scope.empList = msg.data;
      });
    }


}]);



