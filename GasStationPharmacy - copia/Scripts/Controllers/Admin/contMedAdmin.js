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
        var url = 'http://'+getIp()+':58706/api/Productos';
        $http.get(url).then(function(msg){
          client = msg.data;
          $scope.medList = client;
        });

      }

      

      $scope.edit=function(name,id){
      }

      $scope.delete=function(id,prov,nme,med,presc){
        var url = 'http://'+getIp()+':58706/api/Productos';
        var data={
          "idProducto":id,
          "Proveedor":prov,
          "Nombre":nme,
          "esMedicamento":med,
          "reqPrescripcion":presc
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
    //this.getProducts();


    }]);


