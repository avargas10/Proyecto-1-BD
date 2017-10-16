var app=angular.module("pedidoStore", []);

app.controller("contPedidoStore",["$scope","$http", 
  function($scope,$http) {
    $scope.state = "";
    $scope.listOrders=[
        {"idPedido":1,"Estado":1,'date':'03/06/2017 01:20',"punsih":1},
        {"idPedido":2,"Estado":1,'date':'02/06/2017 02:20',"punsih":1},
        {"idPedido":3,"Estado":1,'date':'02/06/2017 03:20',"punsih":2},
        {"idPedido":4,"Estado":1,'date':'02/06/2017 01:20',"punsih":4},



    ];
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
            var url = 'http://192.168.43.110:58706/api/Pedidos';
            $http.get(url).then(function(msg){
                $scope.listOrders= msg.data;
                $scope.state=listOrders.Estado;
                alert($scope.state);
            });
        };


        $scope.update=function(ste){
          var id=ste.split("",1);
            alert(id);
            animation();
        }

    }]);


function animation() {
    var x = document.getElementById("snackbar")
    x.className = "show";
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
}