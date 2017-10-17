
angular.module("mainModule").controller("contEstAdmin",["$scope","$http","userService",
  function($scope,$http,userService) {


    $scope.getHttp= function(url , callback){
      $scope.prod;
      var httpObject = $http.get(url);
      httpObject.then(function(promise){
        callback(promise.data);
      }, function(error){ console.log(error);})}

      $scope.postHttp = function(url,data,callback){
        var httpObject = $http.post(url,data);
        httpObject.then(function(promise){
          callback(promise.data);
        }, function(error){ console.log(error);})}


        $scope.init=function(){
        var url = 'http://'+getIp()+':58706/api/Productos?idEmpresa='+userService.getCompany();
        $http.get(url).then(function(msg){
        if(msg.data.length==0){alert("No Sells Yet");}
        else{ $scope.prod = msg.data;
         process(msg.data);
        $scope.allOrders();
        }
        
        }
      );
    }

      $scope.allOrders=function(){
        var url = 'http://'+getIp()+':58706/api/Pedidos?idEmpresa='+userService.getCompany();
        $http.get(url).then(function(msg){
          $scope.all = msg.data.conteoPedidos; 
          console.log($scope.all);       
          }
        );
      }
      function process(prod){
        var sub={nombre:null,cantidad:null,color: "#FF0F00"};
        var productos=[];

        for(var i=0;i<prod.length ;i++){
          sub={nombre:null,cantidad:null};;
          var nme=prod[i].nombreProducto;
          var qty=prod[i].sumaCantidad;
          sub.nombre=nme;
          sub.cantidad=qty;
          productos.push(sub);
          console.log("sub: "+sub);
          console.log("productos: "+productos);

        }
        create(productos);
      }




    }]);


function create(p){
 
  var total=[];
  for (var index = 0; index < p.length; index++) {
    var data = {
      "country":p[index].nombre,
      "visits": p[index].cantidad,
      "color": "#FF0F00"
    }
    total.push(data);
  }
  var chart = AmCharts.makeChart("chartdiv", { 
    "type": "serial",
    "theme": "light",
    "marginRight": 70,
    "dataProvider": total,
    "valueAxes": [{
      "axisAlpha": 0,
      "position": "left",
      "title": "Quantity"
    }],
    "startDuration": 1,
    "graphs": [{
      "balloonText": "<b>[[category]]: [[value]]</b>",
      "fillColorsField": "color",
      "fillAlphas": 0.9,
      "lineAlpha": 0.2,
      "type": "column",
      "valueField": "visits"
    }],
    "chartCursor": {
      "categoryBalloonEnabled": false,
      "cursorAlpha": 0,
      "zoomable": false
    },
    "categoryField": "country",
    "categoryAxis": {
      "gridPosition": "start",
      "labelRotation": 45
    },
    "export": {
      "enabled": true
    }

  });






  var chart = AmCharts.makeChart( "chartdivr", {
    "type": "pie",
    "theme": "light",
    "dataProvider": [ {
      "company": "BombaTica",
      "sell": 5000
    }, {
      "company": "Phischell",
      "sell": 3590
    }],
    "valueField": "sell",
    "titleField": "company",
    "balloon":{
     "fixedPosition":true
   },
   "export": {
    "enabled": true
  }
} );

}


    
