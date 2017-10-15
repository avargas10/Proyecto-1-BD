angular.module("mainModule").controller("contNuevRolAdmin", ["$scope","$http",
function($scope,$http) {
  $scope.idEmp = "";
  $scope.name="";
  $scope.desc="";

  $scope.createRol=function(nme,descr,emp){
    emp=parseInt(emp.split("",1));
    var url='http://'+getIp()+':58706/api/Roles';
    var sendData={"Nombre": nme, "Descripcion":descr, "idEmpresa":emp};


    $http.post(url,sendData)
    .then(
      function successCallback(response){

      },function errorCallBack(response){

      });
    animation();
  };

    $scope.init = function(){
      url = 'http://'+getIp()+':58706/api/Empresa';
      $http.get(url).then(function(msg){
        $scope.empList = msg.data;
      });
    }




}]);



