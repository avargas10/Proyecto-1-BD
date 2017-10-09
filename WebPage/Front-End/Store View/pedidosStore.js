var app=angular.module("pedidoStore", []);

app.controller("contPedidoStore",["$scope","$http", 
  function($scope,$http) {
    $scope.state = "";
    $scope.listOrders;
    $scope.statesId=[
    {'id':'1:','estado':'Nuevo'},
    {'id':'2:','estado':'Preparado'},
    {'id':'3:','estado':'Facturado'},
    {'id':'4:','estado':'Retirado'}
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