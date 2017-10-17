
angular.module("mainModule").controller("contMedNuevAdmin", ["$scope","$http",'$location',"userService",
function($scope,$http,$location,userService) {
  $scope.id = "";
  $scope.name="";
  $scope.presc="";
  $scope.house="";
  $scope.medicine="";
  $scope.price="";
  $scope.qty="";
  $scope.provList;
  $scope.idAdmin=empresaAdmin;


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
        var url = 'http://'+getIp()+':58706/api/Proveedores';
        $http.get(url).then(function(msg){
          $scope.provList = msg.data;
        });
      }


      $scope.createProduct=function(idP, prov, nme, med, prsc, prc, quant){
        idP=parseInt(idP);
        prov=parseInt(prov.split("",1)[0]);
        med=parseInt(med.split("",1)[0]);
        prsc=parseInt(prsc.split("",1)[0]);
        prc=parseInt(prc);
        quant=parseInt(quant)
        var image=globalImage;
        image=image.split(",")[1];
        
        var url='http://'+getIp()+':58706/api/Productos';
        var sendData={
          "idProducto": idP,
          "Proveedor": prov ,
          "Nombre": nme,
          "esMedicamento": med,
          "reqPrescripcion":prsc,
          "Image": image,
          "Precio":prc,
          "Cantidad":quant,
          "Estado":1
        };
        $scope.postHttp(url,sendData,(data)=>{
          if(data){
            saveProdSuc(idP,quant,prc);
          }
        });

      };
      function saveProdSuc(id,quant,price){
        console.log(id+" "+quant+" "+price);
        var url='http://'+getIp()+':58706/api/ProductoSucursal';
        var sendData= {
          "idSucursal": userService.getSucursal(),
          "codProducto": id,
          "Cantidad": quant,
          "Precio": price
        };
        $scope.postHttp(url,sendData,(data)=>{
          if(data){
            $location.path('/Admin/gmedicamentos')
          }
        });
      }
    }]);
