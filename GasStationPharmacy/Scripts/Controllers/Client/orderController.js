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
      if(!isBlank(phoneNumber)){
      if(!storeService.isEmpty()){
      if(needPres()){
        if(!isEmpty(globalImage)){
          sendData.Imagen= globalImage.split(",")[1];
          $scope.postHttp(url,sendData,(data)=>{
              addOrderDetail(data);
              globalImage="";
          })
        }
        else{alert("Prescription Image is require");}
      }
      else{
        sendData.Imagen= '';
        $scope.postHttp(url,sendData,(data)=>{
          addOrderDetail(data);
          globalImage="";
      })
      }
     }
      else{alert("Shopping bag is empty");
     }
    }
    else{alert("Data is Missing");
  }
    }

     

    function addOrderDetail(id){
      var url = 'http://'+getIp()+':58706/api/DetallePedido';
      var bag = storeService.getBag();
      for (var index = 0; index < bag.length; index++) {
        var sendData = {
          idProducto: bag[index].product.idProducto,
          idPedido: id.idPedido,
          Cantidad: (bag[index].totalPrice/bag[index].product.Precio),
          idSucursal: storeService.getStore().idSucursal
        };
        console.log("idProducto: "+sendData.idProducto);
        console.log("idPedido: "+sendData.idPedido);
        console.log("idCantidad: "+sendData.idCantidad);
        $scope.postHttp(url,sendData,(data)=>{
          console.log("added order detail");
        })
      }
      storeService.cleanBag();
      $location.path("/allOrders");
      //$location.path("/stores");

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
    function confirmDelete(order){
      url= 'http://'+getIp()+':58706/api/Pedidos/'+order;
      $http.delete(url)
      .then(function successCallback(data) {
        alert("Order "+order+" deleted");
        $scope.getAllOrders();
      },
      function errorCallback(response) {
        alert(response);
      });
    }

    $scope.myOrder=function(order){
      console.log("order "+order);
      url= 'http://'+getIp()+':58706/api/Productos?idPed='+order;
      $http.get(url)
      .then(function successCallback(data) {
        console.log(data);
        orderService.setEditOrder(data.data);
        $scope.getEditOrder();
      },
      function errorCallback(response) {
        alert(response);
      });
    }

    $scope.editOrder=function(order){
      console.log("order "+order);
      url= 'http://'+getIp()+':58706/api/Productos?idPed='+order;
      $http.get(url)
      .then(function successCallback(data) {
        console.log(data);
        orderService.setEditOrder(data.data);
        $location.path('/order'); 
      },
      function errorCallback(response) {
        alert(response);
      });
    }

    $scope.deleteDetail=function(detail,order,products,store){
      if(products.length==1){
        alert("Order can't be empty");
        return null;
      }
      
      url= 'http://'+getIp()+':58706/api/DetallePedido?idPedido='+order+'&idProducto='+detail+
      '&idSucursal='+store;
      $http.delete(url)
      .then(function successCallback(data) {
        alert("Product "+detail+" deleted");
        $scope.myOrder(order);
      },
      function errorCallback(response) {
        alert(response);
      });
    }

    $scope.getEditOrder=function(){
      currentImage = orderService.getEditOrder()[0].Imagen;
      $scope.editOrder = orderService.getEditOrder();
      
    }

    $scope.deleteOrder=function(order){  
      console.log(order.Estado+" estado");
      if((order.Estado==2) || (order.Estado==3)){
        if (confirm('If you delete this Order, you will be penalized\nWant to delete?')) {
          confirmDelete(order.idPedido);
          }
      }
      else{confirmDelete(order.idPedido);}
      
     
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
            console.log(data.data);
            orderService.setOrder(data.data);
            $location.path("/order");
          },
          function errorCallback(response) {
            alert(response);
          });
      };

      $scope.updateOrder=function(order,phoneNumber,date){
        var url = 'http://'+getIp()+':58706/api/Pedidos';
        var sendData={
          idPedido: order[0].idPedido,
          sucursalRecojo: order[0].sucursalRecojo,
          idCliente: order[0].idCliente,
          horaRecojo: date,
          Telefono: parseInt(phoneNumber),
          Imagen: currentImage,
          Estado: 1
        };
        
        if(!isEmpty(globalImage)){
          sendData.Imagen=globalImage.split(",")[1];
        }
        console.log(sendData);
        $http.put(url,sendData)
        .then(
            function(response){
              // success callback
              console.log("update");
            }, 
            function(response){
              // failure callback
            }
         );
      }

      $scope.verifyDetail=function(order,quantity){
        if( typeof quantity === 'undefined' || quantity === null ){
          return null;
          }
        var url='http://'+getIp()+':58706/api/Productos/';
        var sendData={
               codProducto: order.idProducto,
               idSucursal: order.sucursalRecojo,
               Cantidad: quantity,
        };
            $http.put(url,sendData)
            .then(function successCallback(data) {
              updateDetail(order,quantity);
            },
            function errorCallback(response) {
              alert("Reduce the number of "+order.Nombre);
            });
      }

      function updateDetail(order,quantity){
        var url = 'http://'+getIp()+':58706/api/DetallePedido';
        var sendData={
          idPedido: order.idPedido,
          idSucursal: order.sucursalRecojo,
          idProducto: order.idProducto,
          Cantidad: quantity,
        };
        
        $http.put(url,sendData)
        .then(
            function(response){
              // success callback
              alert("Updated");
            }, 
            function(response){
              // failure callback
            }
         );
      }

      $scope.delete=function(id){
        var url = 'http://'+getIp()+':58706/api/DetallePedido?idPedido='+id;
        $http.put(url)
        .then(
            function(response){
              // success callback
              console.log("erase");
              $location.path('/allOrders');
            }, 
            function(response){
              // failure callback
            }
         );
      }

      function verifyNumber(product,number){
        var url='http://'+getIp()+':58706/api/Productos';
        var sendData={
               codProducto: product.idProducto,
               idSucursal: product.idSucursal,
               Cantidad: number,
               Precio:product.Precio
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


      $scope.getOrder=function(){$scope.tempOrder =  orderService.getOrder();
      }


  }]);
