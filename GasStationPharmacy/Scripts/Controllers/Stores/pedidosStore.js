angular.module("mainModule").controller("pedidosStore",["$scope","$http", "userService", 
  function($scope,$http, userService) {
    $scope.state = "";
    $scope.listOrders=[];
    $scope.ProductsOrders=[];
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
            var url = 'http://'+getIp()+':58706/api/Pedidos?idSucursal='+userService.getSucursal();
            $http.get(url).then(function(msg){
                console.log(msg.data);
                $scope.listOrders= msg.data;
                for (i = 0; i < msg.data.length; i++) { 
                    var idPed = msg.data[i].idPedido;
                    console.log("idPedido: "+idPed);
                    var url2 = 'http://'+getIp()+':58706/api/DetallePedido/'+ idPed;
                    $http.get(url2).then(function(Data){
                        for (j = 0; j < Data.data.length; j++) {
                        console.log("id de producto: " + Data.data[j].idProducto);
                        $scope.ProductsOrders.push(Data.data[j]);
                        }
                    });
                }
                
            });
        };
        
        $scope.update=function(ste, pedido){
            var id=ste.split(":",1);
            var newid = id[0];
            var url = 'http://'+getIp()+':58706/api/EstadosPedido';
            var json = {"idPedido":pedido,"Estado":newid};
            $http.post(url,json).then(function(msg){
                console.log(msg.data);
                $scope.init();
            });
        }
    
    }]);