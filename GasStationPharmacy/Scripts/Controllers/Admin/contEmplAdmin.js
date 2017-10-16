angular.module("mainModule").controller("contEmplAdmin",["$scope","$http",
function($scope,$http) {
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
        var url = 'http://'+getIp()+':58706/api/Empleados';
        $http.get(url).then(function(msg){
          emp= msg.data;
          $scope.employeelist = emp;
        });

      };


      $scope.edit=function(){
      }

      $scope.delete=function(id,user,pass,nme,pAp,sAp,date,dir,ste){
        var url = 'http://'+getIp()+':58706/api/Empleados';
        var data={
          "idEmpleado":id,
          "Username":user,
          "Password":pass,
          "Nombre":nme,
          "pApellido":pAp,
          "sApellido":sAp,
          "Nacimiento":date,
          "Direccion":dir,
          "Estado":ste

        };
        $http.delete(url,data)
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

    }]);