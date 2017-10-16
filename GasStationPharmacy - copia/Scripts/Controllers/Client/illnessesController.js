angular.module("mainModule").controller("illController", [ "orderService","$scope", "$http", 
"$location", "$routeParams", "userService", 'storeService','illnessService',

  function (orderService,$scope, $http, $location, $routeParams, userService, storeService,illnessService) {
    $scope.createIllness=function(name,date,des){
        if(verifyEmpty(name,date,des)){
        var url = 'http://'+getIp()+':58706/api/Padecimientos';
        var sendData=
        {
            "idUsuario": userService.getUser().Cedula,
            "Fecha": date,
            "Nombre": name,
            "Descripcion": des
          };
          $scope.postHttp(url,sendData,(data)=>{
            console.log("illness created")
          })
    }
    else{
        alert('Data is missing');
    }
    }
    function verifyEmpty(name,date,des){
        if(!isBlank(name),!isBlank(date),!isBlank(des)){
            return true;
        }
        return false;
    }

    $scope.updateIll=function(id,name,date,des){
       
        if(verifyEmpty(name,date,des)){
            var url = 'http://'+getIp()+':58706/api/Padecimientos';
            var sendData=
            {
                "idPadecimiento": id,
                "idUsuario": userService.getUser().Cedula,
                "Fecha": date,
                "Nombre": name,
                "Descripcion": des
              };
              $http.put(url,sendData)
              .then(
                  function(response){
                    // success callback
                    console.log("update");
                    $location.path('/illnesses');
                  }, 
                  function(response){
                    // failure callback
                  }
               );

        }
        else{
            alert('Data is missing');
        }
    }

    $scope.getIllness=function(id){
        var url = 'http://'+getIp()+':58706/api/Padecimientos?idPad='+id;
        $http.get(url)
          .then(function successCallback(data) {
            console.log(data);
            illnessService.setIll(data.data);
            $location.path('/editIllness');
          },
          function errorCallback(response) {
            alert(response);
          });
    }

    $scope.getIll=function(){
        $scope.ill = illnessService.getIll();
    }
    
    $scope.getAllIllness=function(){
        var url = 'http://'+getIp()+':58706/api/Padecimientos/'+userService.getUser().Cedula;
        $http.get(url)
          .then(function successCallback(data) {
            console.log(data);
            $scope.ilnesses = data.data;
          },
          function errorCallback(response) {
            alert(response);
          });
      };
  }]);
