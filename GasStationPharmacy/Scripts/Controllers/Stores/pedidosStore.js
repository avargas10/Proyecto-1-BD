angular.module("mainModule").controller("pedidosStore",["$scope","$http", 
  function($scope,$http) {
    $scope.state = "";
    $scope.listOrders=[];
    $scope.statesId=[
    {'id':'1:','Estado':'Nuevo'},
    {'id':'2:','Estado':'Preparado'},
    {'id':'3:','Estado':'Facturado'},
    {'id':'4:','Estado':'Retirado'}
    ];


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
            var url = 'http://'+getIp()+':58706/api/Pedidos';
            $http.get(url).then(function(msg){
                console.log(msg.data);
                $scope.listOrders= msg.data;
            });
        };

        $scope.update=function(ste){
          var id=ste.split("",1);
            alert(id);
            animation();
        }
    
    }]);