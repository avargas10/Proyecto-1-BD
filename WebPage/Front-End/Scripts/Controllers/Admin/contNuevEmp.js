angular.module("mainModule").controller("contNuevEmp", ["$scope","$http",

  function($scope,$http) {
    $scope.id;
    $scope.name;
    $scope.empEmail;
    $scope.empUser;
    $scope.userPass;
    $scope.empSur;


    $scope.createEmp=function(idE,mail,uName,pss,nme,pAp){


      var url='http://'+getIp()+':58706/api/Sucursal';
      var sendData={
        "idEmpleado": id,
        "Email": idP,
        "idCanton":idC, 
        "idDistrito":idD,
        "Latitud":lt,
        "Longitud":ln,
        "detalleDireccion":dr,
        "Nombre":nme ,
        "Estado":1
      };
      /**
      $scope.postHttp(url,sendData,(data)=>{
          if(data){

          }
        });
  **/
        
      animation();
    }

  }]);