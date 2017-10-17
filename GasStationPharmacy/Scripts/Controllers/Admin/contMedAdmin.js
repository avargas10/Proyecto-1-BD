angular.module("mainModule").controller("contMedAdmin",["$scope","$http","userService",
function($scope,$http,userService) {
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

      $scope.provedores = function(){
        var url = 'http://'+getIp()+':58706/api/Proveedores';
        $http.get(url).then(function(msg){
          $scope.provList = msg.data;
        });
      }

      $scope.init = function(){
        var url = 'http://'+getIp()+':58706/api/Productos?idSucursal='+userService.getSucursal();
        $http.get(url).then(function(msg){
          client = msg.data;
          $scope.medList = client;
        });

      }

      

      $scope.update=function(id,quantity,price){
        var url='http://'+getIp()+':58706/api/ProductoSucursal';
        var sendData={
          "idSucursal": userService.getSucursal(),
          "codProducto": id,
          "Cantidad": quantity,
          "Precio": price
        };
            $http.put(url,sendData)
            .then(function successCallback(data) {
              console.log("responde true: "+ data.data);
                console.log("pass verify")
            },
            function errorCallback(response) {
              alert("Reduce the number of "+product.Nombre);
              $location.path("/order");
              
             
            });

      }

      $scope.delete=function(id,nme){
        var url = 'http://'+getIp()+':58706/api/Productos';
        var data={
          "idProducto":id,
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
        alert("Product "+nme+" deleted");
        $scope.init();
      }, function(rejection) {
          console.log(rejection.data);
      });
      
    }


    }]);


