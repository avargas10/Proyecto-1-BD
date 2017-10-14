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
        alert(this.medList);
        var url = 'http://'+getIp()+':58706/api/Productos';
        $http.get(url).then(function(msg){
          client = msg.data;
          this.medList = client;
        });

      }
      
      $scope.edit=function(name,id){
      }

      $scope.delete=function(id){
        var url = 'http://'+getIp()+':58706/api/Productos?idProducto='+id;
        $http.put(url)
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


