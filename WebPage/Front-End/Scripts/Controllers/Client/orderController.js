angular.module("mainModule").controller("orderController", [ "orderService","$scope", "$http", "$location", "$routeParams", "userService", 'storeService',

  function (orderService,$scope, $http, $location, $routeParams, userService, storeService) {
    
    $scope.placeOrder=function(phoneNumber,date){

      var url = 'http://'+getIp()+':58706/api/Pedidos';
      var sendData={
        sucursalRecojo: storeService.getStore().idSucursal,
        idCliente: userService.getUser().Cedula,
        horaRecojo: date,
        Telefono: parseInt(phoneNumber),
        Imagen: globalImage,
        Estado: 1
      };
      console.log("image: "+needPres());
      if(!storeService.isEmpty()){
      if(needPres()){
        if(!isEmpty(globalImage)){
          sendData.Imagen= globalImage.split(",")[1]
          $scope.postHttp(url,sendData,(data)=>{
              addOrderDetail(data);
              globalImage="";
          })
        }
        else{alert("Prescription Image is require");}
      }
      else{
        $scope.postHttp(url,sendData,(data)=>{
          addOrderDetail(data);
          globalImage="";
      })
      }
     }
      else{alert("Shopping bag is empty");
     }}

    function addOrderDetail(id){
      var url = 'http://'+getIp()+':58706/api/DetallePedido';
      var bag = storeService.getBag();
      for (var index = 0; index < bag.length; index++) {
        var sendData = {
          idProducto: bag[index].product.idProducto,
          idPedido: id.idPedido,
          idCantidad: (bag[index].totalPrice/bag[index].product.Precio)
        };
        console.log("idProducto: "+sendData.idProducto);
        console.log("idPedido: "+sendData.idPedido);
        console.log("idCantidad: "+sendData.idCantidad);
        $scope.postHttp(url,sendData,(data)=>{
          console.log("added order detail");
        })
      }
      storeService.cleanBag();
    }

    function isEmpty(str) {
      return (!str || 0 === str.length);
    }
    function needPres(){
      var bag  = storeService.getBag();
      for (var index = 0; index < bag.length; index++) {
        if(bag[index].product.reqPrescripcion==1){
          return true;
        }
      }
      return false;
    }

    $scope.getAllOrders=function(){
        var url = 'http://'+getIp()+':58706/api/Pedidos/'+userService.getUser().Cedula;
        $http.get(url)
          .then(function successCallback(data) {
            console.log(data);
            $scope.Orders = data.data;
          },
          function errorCallback(response) {
            alert(response);
          });
      };
      $scope.getAllDetails=function(id){
        console.log("entra");
        var url = 'http://'+getIp()+':58706/api/Productos?idPed='+id;
        $http.get(url)
          .then(function successCallback(data) {
            console.log(data);
            $scope.currentOrder= data;
            $location.path("/order");
          },
          function errorCallback(response) {
            alert(response);
          });
      };
  }]);
