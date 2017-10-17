angular.module("mainModule").controller("pedidosStore",["$scope","$http", "$location","userService", "orderService",  
  function($scope,$http, $location, userService, orderService) {
    $scope.state = "";
    $scope.listOrders=[];
    $scope.ProductOrders=[];
    var pedidoselected=1;
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
            var url = 'http://'+getIp()+':58706/api/Pedidos?idSuc='+userService.getSucursal();
            $http.get(url).then(function(msg){
                console.log(msg.data);
                $scope.listOrders= msg.data;
            });
        };
        $scope.initProduct = function(){
            console.log("PEdido Selected: " + orderService.getCurrentOrder());
            var url = 'http://'+getIp()+':58706/api/Productos?Ped='+orderService.getCurrentOrder();
            $http.get(url).then(function(msg){
                console.log(msg.data);
                $scope.ProductOrders= msg.data;
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
        $scope.see=function(pedido){
            orderService.setCurrentOrder(pedido);
            $location.path("/Stores/ProductsxOrder");
        }
    
    }]);